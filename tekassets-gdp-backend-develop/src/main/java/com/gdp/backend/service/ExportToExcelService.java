package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.dto.OpportunityDto;
import com.gdp.backend.dto.ProjectStatusDto;
import com.gdp.backend.dto.SearchFilterDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;

/**
 * This Service class used for export project data into the excel files either all Project details or based on condition data will be populate in excel file.
 * @author gdp
 *
 */
@Service
public class ExportToExcelService {

    @Autowired
    ProjectStatusService projectStatusService;

    @Autowired
    OpportunityService opportunityService;

    @Autowired
    GDPUserDetailsService gdpUserDetailsService;

    /**
     * This Service would be use for getting the list of project details and populate in excel file.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @return response this class contains Project details.
     * @throws ActionFailureException exception would be throws if Project data not found.
     */
    public ByteArrayInputStream getAllProjectDetails(HttpServletResponse httpServletResponse) throws ActionFailureException {
        Set<ProjectStatusDto> projectList = projectStatusService.getProjectStatusDefaultValue(0, 0, gdpUserDetailsService.getUserOpcoId()).getProjectStatusDtoList();
        return exportToExcel(httpServletResponse, projectList,"0");
    }

    /**
     * This Service would be use for getting the list of Opportunity details and populate into the excel file.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @return this method would be return ByteArrayInputStream.
     * @throws ActionFailureException exception would be throws if Opportunity data not found.
     */
    public ByteArrayInputStream getAllOpportunityDetails(HttpServletResponse httpServletResponse,String OpcoId) throws ActionFailureException {
        List<OpportunityDto> opportunityList = opportunityService.getAllOpportunityValues(0, 0, OpcoId ).getOpportunityDtoList();
        return opportunityExportToExcel(httpServletResponse, opportunityList);
    }

    /**
     * This Service would be use for getting the list of project details and populate in excel file based on search data.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @param searchFilterDto is the search filter data.
     * @return this method would be return ByteArrayInputStream.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public ByteArrayInputStream getProjectDetailsOnSearch(HttpServletResponse httpServletResponse, SearchFilterDto searchFilterDto ,String opcoId ) throws ActionFailureException {
        Set<ProjectStatusDto> projectList = projectStatusService.getProjectStatusOnSearch(searchFilterDto, 0, 0 , opcoId).getProjectStatusDtoList();
        return exportToExcel(httpServletResponse, projectList,opcoId);
    }

    /**
     * This method would be use for exporting the project details and populate in excel file.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @param projectDetailsList is the set of Project Details.
     * @return this method would be return ByteArrayInputStream.
     * @throws ActionFailureException exception would be throws if Project data not found.
     */
    public ByteArrayInputStream exportToExcel(HttpServletResponse httpServletResponse, Set<ProjectStatusDto> projectDetailsList, String OpcoId)
            throws ActionFailureException {
        String[] columns={};
        if (!"2".equals(OpcoId)) {
            columns = new String[]{"Engagement Name", "Account Name", "GDP ID", "PeopleSoft Project ID", "GDD", "GDM", "PrgM", "EM / DL", "Status Date", "Current Phase", "Status Indicator", "Start Date", "End Date", "Location", "Summary", "Practice", "Business Unit"};
        } else if (OpcoId.equals("2")) {
            columns = new String[]{"Engagement Name", "Account Name", "GDP ID", "PeopleSoft Project ID", "GDD", "GDM", "PrgM", "EM / DL", "Status Date", "Current Phase", "Status Indicator", "Start Date", "End Date", "Location", "Summary", "Business Unit"};
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            /* create excel xls sheet */
            Sheet sheet = workbook.createSheet("All Engagement Details");
            sheet.setDefaultColumnWidth(30);

            /* create style for header cells */
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            style.setFont(font);

            /* Row for Header */
            Row headerRow = sheet.createRow(0);

            /* create header row */
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(style);
            }
            int rowCount = 1;
            for (ProjectStatusDto project : projectDetailsList) {
                Row projectRow = sheet.createRow(rowCount++);
                projectRow.createCell(0).setCellValue(project.getProjectName());
                projectRow.createCell(1).setCellValue(project.getAccountName());
                projectRow.createCell(2).setCellValue(project.getGdpId());
                projectRow.createCell(3).setCellValue(project.getPsId());
                projectRow.createCell(4).setCellValue(project.getGdd()==null?"":project.getGdd().trim());
                System.out.println("########## PSID  "+project.getPsId() +"########## GDD  "+project.getGdd());
                projectRow.createCell(5).setCellValue(project.getGdm()==null?"":project.getGdm().trim());
                projectRow.createCell(6).setCellValue(project.getPm()==null?"":project.getPm().trim());
                projectRow.createCell(7).setCellValue(project.getCsl()==null?"":project.getCsl().trim());
                projectRow.createCell(8).setCellValue(project.getStatusDate());
                projectRow.createCell(9).setCellValue(project.getCurrentPhase());
                projectRow.createCell(10).setCellValue(project.getStatus());
                projectRow.createCell(11).setCellValue(project.getStartDate());
                projectRow.createCell(12).setCellValue(project.getEndDate());
                projectRow.createCell(13).setCellValue(project.getLocation());
                projectRow.createCell(14).setCellValue(project.getStatusSummary());
                if(!"2".equals(OpcoId)) {
                    projectRow.createCell(15).setCellValue(project.getPractice());
                }
                if("2".equals(OpcoId)) {
                    projectRow.createCell(15).setCellValue(project.getBusinessUnit());
                }
                else{
                    projectRow.createCell(16).setCellValue(project.getBusinessUnit());
                }
            }
            workbook.write(outputStream);
            httpServletResponse.flushBuffer();
            outputStream.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        }
    }

    /**
     * This method would be use for exporting Opportunity into the excel file.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @param opportunityList list of opportunity.
     * @return this method would be return ByteArrayInputStream.
     * @throws ActionFailureException exception would be throws if Opportunity data not found.
     */
    public ByteArrayInputStream opportunityExportToExcel(HttpServletResponse httpServletResponse,List<OpportunityDto> opportunityList)
            throws ActionFailureException {
        String[] columns = {"Opportunity Name", "Account Name", "Opportunity ID", "Opportunity End Date",
                "Engagement Start Date","GS Opportunity Type", "Service Type", "Delivery Model","Practice"};
        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            /* create excel xls sheet */
            Sheet sheet = workbook.createSheet("All Opportunity Details");
            sheet.setDefaultColumnWidth(30);

            /* create style for header cells */
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            style.setFont(font);

            /* Row for Header */
            Row headerRow = sheet.createRow(0);

            /* create header row */
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(style);
            }
            int rowCount = 1;
            for (OpportunityDto opportunity : opportunityList) {
                Row projectRow = sheet.createRow(rowCount++);
                projectRow.createCell(0).setCellValue(opportunity.getOpportunityName());
                projectRow.createCell(1).setCellValue(opportunity.getAccountName());
                projectRow.createCell(2).setCellValue(opportunity.getOpportunityId());
                projectRow.createCell(3).setCellValue(opportunity.getClosedDate());
                projectRow.createCell(4).setCellValue(opportunity.getStartDate());
                projectRow.createCell(5).setCellValue(opportunity.getGsOpportunity());
                projectRow.createCell(6).setCellValue(opportunity.getPrimaryService());
                projectRow.createCell(7).setCellValue(opportunity.getDeliveryModel());
                projectRow.createCell(8).setCellValue(opportunity.getPractice());
            }
            workbook.write(outputStream);
            httpServletResponse.flushBuffer();
            outputStream.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        }
    }
}
