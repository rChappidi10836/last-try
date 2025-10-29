package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.common.Utils;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.mapper.AdminTabMapper;
import com.gdp.backend.model.GDPArchivedPracticeDetails;
import com.gdp.backend.repository.DeliveryHoursRepository;
import com.gdp.backend.repository.GDPArchivedPracticeDetailsRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This Service class used for create Excel file For ManageAccess, ProjectReport, MilestoneDeliveryReport, MilestoneAgileSprintReport, SecurityProfileReport, PMLCScorecardReport and ProjectArchiveData.
 *
 * @author gdp
 */
@Service
public class ExcelService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private GDPArchivedPracticeDetailsRepository archivedPracticeDetailsRepository;
    @Autowired
    private DeliveryHoursRepository deliveryHoursRepository;
    private CreationHelper createHelper;

    @Autowired
    GDPUserDetailsService gdpUserDetailsService;

    /**
     * This service would be used to creating Excel file for Manage Access details.
     * @param isActive is the boolean.
     * @param outputStream is use to write the  Manage Access details into the excel file.
     * @throws ActionFailureException exception would be throws, if ManageAccessExcel not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */
    public void createExcelForManageAccess(boolean isActive,String opcoId, OutputStream outputStream)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("ResourcesWithRoles");

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            createCell(row, columnCount++, "Employee Name", style);
            createCell(row, columnCount++, "Employee ID", style);
            createCell(row, columnCount++, "User Name", style);
            createCell(row, columnCount++, "Title", style);
            createCell(row, columnCount++, "Supervisor Name", style);
            createCell(row, columnCount++, "Location", style);
            createCell(row, columnCount++, "Hire Date", style);
            if (!isActive)
                createCell(row, columnCount++, "Termination Date", style);
            createCell(row, columnCount++, "Access", style);

            // creating rows
            writeManageAccessExcel(workbook, sheet, isActive,opcoId);

            workbook.write(outputStream);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }

    /**
     * This service would be used to Exporting the Excel file.
     * @param tableName is the name of excel file.
     * @param outputStream is use to write the project details into the excel file.
     * @throws ActionFailureException exception would be throws, if Excel not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */
    public void createExcelForExport(String tableName, OutputStream outputStream,Boolean active, Integer opcoId)
            throws ActionFailureException, IOException {
        // used to map table name to heading name in excel
        Map<String, String> tableToHeading = new HashMap<>();
        tableToHeading.put("DeliveryModel", Constants.DELIVERY_MODEL);
        tableToHeading.put("PrimaryServiceType", Constants.SERVICE_TYPE);
        tableToHeading.put("StaffingSalesRegion", Constants.STAFFING_SALES_REGION);
        tableToHeading.put("DeliveryOrganizationType", Constants.DELIVERY_ORGANIZATION);
        tableToHeading.put("Location", Constants.LOCATION_OF_SERVICES);
        tableToHeading.put("ContractType", Constants.CONTRACT_TYPE);
        tableToHeading.put("SalesOrganization", Constants.SALES_ORGANIZATION);
        tableToHeading.put("DeliveryMilestoneType", Constants.DELIVERY_MILESTONE);
        tableToHeading.put("Client", Constants.ACCOUNT);

        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet(tableToHeading.get(tableName));

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            sheet.setDefaultColumnWidth(30);

            createCell(row, 0, String.format(Constants.UPDATED_NAME_PATTERN, tableToHeading.get(tableName)),
                    style);
            createCell(row, 1, String.format(Constants.OLD_NAME_PATTERN, tableToHeading.get(tableName)), style);
            createCell(row, 2, Constants.ACTION, style);
            createCell(row, 3, Constants.UPDATED_BY, style);
            createCell(row, 4, Constants.UPDATED_DATE, style);

            // creating rows
            if(tableName.equals("Client")) {
                writeDataLines(workbook, sheet, tableName,active, opcoId);
            }else if(tableName.equals("DeliveryOrganizationType")){
                writeDataLines(workbook, sheet, tableName,active, 0);
            }else{
                writeDataLines(workbook, sheet, tableName,false, 0);
            }
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * This service would be used to creating Excel file for Project Report Data.
     *
     * @param outputStream is use to write the Project Report Data into the excel file.
     * @param isActive     is the boolean value which check the project is active or not.
     * @param opcoID
     * @throws ActionFailureException exception would be throws, if Project Report Data not found.
     * @throws IOException            exception would be throws, if outputStream data not found.
     */
    public void createProjectReportData(OutputStream outputStream, boolean isActive, String opcoID)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("Report Data");

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) (short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "PeopleSoft Project ID", style);
            createCell(row, columnCount++, "PeopleSoft Engagement ID", style);
            createCell(row, columnCount++, "Delivery Model", style);
            if (!"2".equals(opcoID)) {
                createCell(row, columnCount++, "Practice", style);
            }
            createCell(row, columnCount++, "Location of Delivery", style);
            createCell(row, columnCount++, "Opportunity ID", style);
            createCell(row, columnCount++, "SMP Link", style);
            createCell(row, columnCount++, "Business Unit", style);
            createCell(row, columnCount++, "Service Type", style);
            createCell(row, columnCount++, "GDD", style);
            createCell(row, columnCount++, "GDM", style);
            createCell(row, columnCount++, "PrgM", style);
            createCell(row, columnCount++, "EM / DL", style);
            createCell(row, columnCount++, "BDM / AM / SAM", style);
            createCell(row, columnCount++, "National Account Owner", style);
            createCell(row, columnCount++, "OSG POA", style);
            createCell(row, columnCount++, "OSG BOA", style);
            createCell(row, columnCount++, "Sales Organization", style);
//            createCell(row, columnCount++, "Opportunity Name", style); removed
            createCell(row, columnCount++, "Start Date", style);
            createCell(row, columnCount++, "End Date", style);
            createCell(row, columnCount++, "Phase", style);
            createCell(row, columnCount++, "Status Date", style);
            createCell(row, columnCount++, "Summary", style);
            createCell(row, columnCount++, "Schedule", style);
            createCell(row, columnCount++, "Schedule Comments", style);
            createCell(row, columnCount++, "CSAT", style);
            createCell(row, columnCount++, "CSAT Comments", style);
            createCell(row, columnCount++, "Budget", style);
            createCell(row, columnCount++, "Budget Comments", style);
            createCell(row, columnCount++, "Engagement Risk", style);
            createCell(row, columnCount++, "Engagement Risk Comments", style);
            createCell(row, columnCount++, "Resources", style);
            createCell(row, columnCount++, "Resources Comments", style);
            createCell(row, columnCount++, "Status Indicator", style);
            if (!"2".equals(opcoID)) {
                createCell(row, columnCount++, "Risk Profile", style);
                createCell(row, columnCount++, "Risk Survey Date", style);
                createCell(row, columnCount++, "Security Profile Date", style);
            }
            if(!isActive)

                createCell(row, columnCount++, "Engagement Status", style);

            createCell(row, columnCount++, "Target Technology Platform", style);

            writeReportDataLines(workbook, sheet, isActive,opcoID);

            workbook.write(outputStream);


        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }





    /**
     * This method would be use for writing the  StakeHolders Report Data Lines Excel file.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     * @param isActive is the boolean value which check the project is active or not.
     */

    private void writeStakeHoldersReportDataLines(SXSSFWorkbook workbook, Sheet sheet, boolean isActive,String opcoId) {
        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) (short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder stakeHoldersReportDataExcelQuery = new StringBuilder(Queries.STAKE_HOLDERS_REPORT_DATA_EXCEL_QUERY);
        System.out.println("Opco ID in excl Service is"+gdpUserDetailsService.getUserOpcoId());
        stakeHoldersReportDataExcelQuery.append(Constants.WHERE_CLAUSE);
        stakeHoldersReportDataExcelQuery.append(Constants.OPCO_STATUS);
        stakeHoldersReportDataExcelQuery.append(opcoId);

        Query finalQuery = entityManager.createNativeQuery(stakeHoldersReportDataExcelQuery.toString());
        list.addAll(finalQuery.getResultList());
        int columnCount = 0;

        for (Object[] objects : list) {
            columnCount = 0;

            StakeHoldersDto stakeHoldersDto = StakeHoldersDto.stakeHoldersMapper(objects);

            Row row = sheet.createRow(rowCount++);


            createCell(row, columnCount++, stakeHoldersDto.getProject(), style);
            createCell(row, columnCount++, stakeHoldersDto.getAccountName(), style);
            createCell(row, columnCount++, stakeHoldersDto.getGdpId(), style);
            createCell(row, columnCount++, stakeHoldersDto.getPeopleSoftId(), style);
            createCell(row, columnCount++, stakeHoldersDto.getPeoplesoftEngagementId(), style);
            createCell(row, columnCount++, stakeHoldersDto.getDeliveryModel(), style);
            createCell(row, columnCount++, stakeHoldersDto.getPracticeEngagement(), style);
            createCell(row, columnCount++, stakeHoldersDto.getLocationOfServices(), style);
            createCell(row, columnCount++, stakeHoldersDto.getOpportunityId(), style);
            createCell(row, columnCount++, stakeHoldersDto.getSMPLink(), style);
            createCell(row, columnCount++, stakeHoldersDto.getBusinessUnit(), style);
            createCell(row, columnCount++, stakeHoldersDto.getGlobalDeliveryDirector()==null?"":stakeHoldersDto.getGlobalDeliveryDirector().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getGDM()==null?"":stakeHoldersDto.getGDM().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getPM()==null?"":stakeHoldersDto.getPM().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getCSL()==null?"":stakeHoldersDto.getCSL().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getDL()== null?"":stakeHoldersDto.getDL().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getBDM()==null?"":stakeHoldersDto.getBDM().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getNationalAccountOwner()==null?"":stakeHoldersDto.getNationalAccountOwner().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getOSGPOA()==null?"":stakeHoldersDto.getOSGPOA().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getOSGBOA()==null?"":stakeHoldersDto.getOSGBOA().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getSalesRegion(), style); //sales region is replaced with sales organization in query
//            createCell(row, columnCount++, projectDataDto.getOpportunityOwner(), style); removed
            createCell(row, columnCount++, stakeHoldersDto.getAccountManager()==null?"":stakeHoldersDto.getAccountManager().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getSalesRD()==null?"":stakeHoldersDto.getSalesRD().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getSolutionArchitect()==null?"":stakeHoldersDto.getSolutionArchitect().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getPracticeDirector()==null?"":stakeHoldersDto.getPracticeDirector().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getPracticeManager()==null?"": stakeHoldersDto.getPracticeManager().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getSolutionExecutive()==null?"":stakeHoldersDto.getSolutionExecutive().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getTransformationLead()==null?"":stakeHoldersDto.getTransformationLead().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getProjectManager()==null?"":stakeHoldersDto.getProjectManager().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getProjectCoordinator()==null?"":stakeHoldersDto.getProjectCoordinator().trim(), style);
            createCell(row, columnCount++, stakeHoldersDto.getTrainingCoordinator()==null?"":stakeHoldersDto.getTrainingCoordinator().trim(), style);
//            createCell(row, columnCount++, stakeHoldersDto.getPillarLead()==null?"":stakeHoldersDto.getPillarLead().trim(), style);
//            createCell(row, columnCount++, stakeHoldersDto.getPodLead()==null?"":stakeHoldersDto.getPodLead().trim(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(stakeHoldersDto.getStartDate()), dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(stakeHoldersDto.getEndDate()), dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, stakeHoldersDto.getProjectStage(), style);
            try {
                if (stakeHoldersDto.getDummyRecord()) {
                    createCell(row, columnCount++, "", style);
                } else {
                    createDateCell(row, columnCount++, Utils.getFormattedDate(stakeHoldersDto.getStatusDate()), dateStyle);
                }
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, stakeHoldersDto.getSummary(), style);
            createCell(row, columnCount++, stakeHoldersDto.getSchedule(), style);
            createCell(row, columnCount++, stakeHoldersDto.getScheduleComments(), style);
            createCell(row, columnCount++, stakeHoldersDto.getCSAT(), style);
            createCell(row, columnCount++, stakeHoldersDto.getCSATComments(), style);
            createCell(row, columnCount++, stakeHoldersDto.getBudget(), style);
            createCell(row, columnCount++, stakeHoldersDto.getBudgetComments(), style);
            createCell(row, columnCount++, stakeHoldersDto.getProjectRisk(), style);
            createCell(row, columnCount++, stakeHoldersDto.getProjectRiskComments(), style);
            createCell(row, columnCount++, stakeHoldersDto.getResources(), style);
            createCell(row, columnCount++, stakeHoldersDto.getResourcesComments(), style);
            createCell(row, columnCount++, stakeHoldersDto.getOverallStatus(), style);
            createCell(row, columnCount++, Utils.getRiskIndicatorValueFromColor(stakeHoldersDto.getRiskProfileIndicator()), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(stakeHoldersDto.getRiskSurveyDate()),
                        dateStyle);
            } catch (Exception ex) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(stakeHoldersDto.getSecurityStatusDate()),
                        dateStyle);
            } catch (Exception ex) {
                createCell(row, columnCount, "", style);
            }
            if (!isActive)
                createCell(row, columnCount, stakeHoldersDto.isActive() ? Constants.ACTIVE : Constants.CLOSED,
                        style);
            stakeHoldersDto = null;
        }

    }

    /**
     * This service would be used to creating Excel file for StakeHolders Report Data.
     * @param outputStream is use to write the StakeHolders Report Data into the excel file.
     * @param isActive is the boolean value which check the project is active or not.
     * @throws ActionFailureException exception would be throws, if StakeHolders Report Data not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */

    public void createStakeHoldersReportData(OutputStream outputStream, boolean isActive,String opcoId)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("All StakeHolders Report");

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) (short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "PeopleSoft Project ID", style);
            createCell(row, columnCount++, "PeopleSoft Engagement ID", style);
            createCell(row, columnCount++, "Delivery Model", style);
            createCell(row, columnCount++, "Practice", style);
            createCell(row, columnCount++, "Location of Delivery", style);
            createCell(row, columnCount++, "Opportunity ID", style);
            createCell(row, columnCount++, "SMP Link", style);
            createCell(row, columnCount++, "Business Unit", style);
            createCell(row, columnCount++, "Global Delivery Director", style);
            createCell(row, columnCount++, "GDM", style);
            createCell(row, columnCount++, "PrgM", style);
            createCell(row, columnCount++, "EM", style);
            createCell(row, columnCount++, "DL", style);
            createCell(row, columnCount++, "BDM / AM / SAM", style);
            createCell(row, columnCount++, "National Account Owner", style);
            createCell(row, columnCount++, "OSG POA", style);
            createCell(row, columnCount++, "OSG BOA", style);
            createCell(row, columnCount++, "Sales Organization", style);
            createCell(row, columnCount++, "Account Manager", style);
            createCell(row, columnCount++, "Sales RD", style);
            createCell(row, columnCount++, "Solution Architect", style);
            createCell(row, columnCount++, "Practice Director ", style);
            createCell(row, columnCount++, "Practice Manager", style);
            createCell(row, columnCount++, "Solution Executive", style);
            createCell(row, columnCount++, "Transformation Lead", style);
            createCell(row, columnCount++, "Project Manager", style);
            createCell(row, columnCount++, "Project Coordinator", style);
            createCell(row, columnCount++, "Training Coordinator", style);
//            createCell(row, columnCount++, "Pillar Lead", style);
//            createCell(row, columnCount++, "POD Lead", style);


//            createCell(row, columnCount++, "Opportunity Name", style); removed


            createCell(row, columnCount++, "Start Date", style);
            createCell(row, columnCount++, "End Date", style);
            createCell(row, columnCount++, "Phase", style);
            createCell(row, columnCount++, "Status Date", style);
            createCell(row, columnCount++, "Summary", style);
            createCell(row, columnCount++, "Schedule", style);
            createCell(row, columnCount++, "Schedule Comments", style);
            createCell(row, columnCount++, "CSAT", style);
            createCell(row, columnCount++, "CSAT Comments", style);
            createCell(row, columnCount++, "Budget", style);
            createCell(row, columnCount++, "Budget Comments", style);
            createCell(row, columnCount++, "Engagement Risk", style);
            createCell(row, columnCount++, "Engagement Risk Comments", style);
            createCell(row, columnCount++, "Resources", style);
            createCell(row, columnCount++, "Resources Comments", style);
            createCell(row, columnCount++, "Status Indicator", style);
            createCell(row, columnCount++, "Risk Profile", style);
            createCell(row, columnCount++, "Risk Survey Date", style);
            createCell(row, columnCount++, "Security Profile Date", style);

            if (!isActive)
                createCell(row, columnCount, "Engagement Status", style);

            writeStakeHoldersReportDataLines(workbook, sheet, isActive,opcoId);

            workbook.write(outputStream);


        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }


    /**
     * This service would be used to creating Excel file for Milestone Delivery Report.
     *
     * @param outputStream is use to write the Milestone Delivery Report into the excel file.
     * @param isActive     is the boolean value which check the project is active or not.
     * @throws ActionFailureException exception would be throws, if Milestone Delivery Report not found.
     * @throws IOException            exception would be throws, if outputStream data not found.
     */
    public void createMilestoneDeliveryReport(OutputStream outputStream, boolean isActive, String opcoId)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("Milestones Delivery Report");

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) (short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "GDD", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "Delivery Type", style);
            createCell(row, columnCount++, "Milestone Description", style);
            createCell(row, columnCount++, "Start Date", style);
            createCell(row, columnCount++, "End Date", style);
            createCell(row, columnCount++, "Comments", style);
            if (!isActive)
                createCell(row, columnCount, "Engagement Status ", style);

            writeMilestoneDeliveryReportLines(workbook, sheet, isActive,opcoId);
            workbook.write(outputStream);

        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }

    /**
     * This service would be used to creating Excel file for Milestone Agile Sprint Report.
     * @param outputStream is use to write the Milestone Delivery Report into the excel file.
     * @param isActive is the boolean value which check the project is active or not.
     * @throws ActionFailureException exception would be throws, if Milestone Agile Sprint Report not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */
    public void createMilestoneAgileSprintReport(OutputStream outputStream, boolean isActive,String opcoId )
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("Milestones Agile Sprint Report");

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "GDD", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "Track", style);
            createCell(row, columnCount++, "Sprint", style);
            createCell(row, columnCount++, "Start Date", style);
            createCell(row, columnCount++, "End Date", style);
            createCell(row, columnCount++, "Delivered Stories", style);
            createCell(row, columnCount++, "Committed Story Points", style);
            createCell(row, columnCount++, "Delivered Story Points", style);
            createCell(row, columnCount++, "Added Stories", style);
            createCell(row, columnCount++, "Removed Stories", style);
            createCell(row, columnCount++, "Capacity (hours)", style);
            createCell(row, columnCount++, "Estimate (hours)", style);
            createCell(row, columnCount++, "Goal Met", style);
            createCell(row, columnCount++, "Sprint Status", style);
            if (!isActive)
                createCell(row, columnCount, "Engagement Status ", style);

            writeMilestoneAgileSprintDataLines(workbook, sheet, isActive,opcoId);

            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }

    /**
     * This service would be used to creating Excel file for Security Profile Report.
     * @param outputStream is use to write the Security Profile Report into the excel file.
     * @param isActive is the boolean value which check the project is active or not.
     * @throws ActionFailureException exception would be throws, if Security Profile Report not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */
    public void createSecurityProfileReport(OutputStream outputStream, boolean isActive,String opcoId)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("TGS IS Security Profile Report ");
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setShrinkToFit(true);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            // merging cells for headings
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 19));


            // headings
            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "GDD", style);
            createCell(row, columnCount++, "EM / DL", style);
            createCell(row, columnCount++, "Practice", style);
            createCell(row, columnCount++, "Location of Delivery", style);
            createCell(row, columnCount++, "SMP Link", style);
            createCell(row, columnCount++, "Opportunity ID", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "End Date", style);
            createCell(row, columnCount, "Questions", style);

            // question font style
            CellStyle questionStyle = workbook.createCellStyle();
            questionStyle.setWrapText(true);
            questionStyle.setAlignment(HorizontalAlignment.CENTER);
            questionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            questionStyle.setShrinkToFit(true);
            Font questionFont = workbook.createFont();
            questionFont.setFontHeightInPoints((short) 11);
            questionStyle.setFont(questionFont);

            // color code for questions as per reference excel.
            byte[] rgb = new byte[]{(byte) 23, (byte) 43, (byte) 77};
            questionFont.setColor(new XSSFColor(rgb, null).getIndex());

            // writing questions in 2nd row
            Row secondRow = sheet.createRow(1);
            createCell(secondRow, columnCount++, "Q1: Does client agreement require dedicated team room space? (No other client support team in the room)", questionStyle);
            createCell(secondRow, columnCount++, "Q2: How are we connecting to the client environment?", questionStyle);
            createCell(secondRow, columnCount++, "Q3: Does client agreement permit remote work from home? (Outside of pandemic conditions)", questionStyle);
            createCell(secondRow, columnCount++, "Q4: Per client agreement, will the team have access to sensitive information?", questionStyle);
            createCell(secondRow, columnCount++, "Q5: Does client agreement permit offshore delivery of services?", questionStyle);
            createCell(secondRow, columnCount++, "Q6: What are the client compliance requirements?", questionStyle);
            createCell(secondRow, columnCount++, "Q7: Does the client agreement include specific business continuity requirements?", questionStyle);
            createCell(secondRow, columnCount++, "Q8: Is cloud environment used in support of project?", questionStyle);
            createCell(secondRow, columnCount++, "Q9: Whose end user assets (laptops) are the delivery team members using? (include all that apply)", questionStyle);
            createCell(secondRow, columnCount++, "Q10: Per client agreement, will the team be storing any customer data on a TEK owned asset or within any TEK cloud environment?", questionStyle);
            createCell(row, columnCount++, "Security Profile Date", style);
            createCell(row, columnCount, "Last Updated User", style);

            writeSecurityProfileReportDataLines(workbook, sheet, isActive,opcoId);

            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }

    /**
     * This service would be used to creating Excel file for PMLCScorecard Report.
     * @param outputStream is use to write the PMLCScorecard Report into the excel file.
     * @throws ActionFailureException exception would be throws, if PMLCScorecard Report not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */
    public void createPMLCScorecardReport(OutputStream outputStream,String opcoId)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("PMO Compliance Report");
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setShrinkToFit(true);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);
            // headings
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "Risk Profile", style);
            createCell(row, columnCount++, "SMP Link", style);
            createCell(row, columnCount++, "GDD", style);
            createCell(row, columnCount++, "GDM", style);
            createCell(row, columnCount++, "EM / DL", style);
//            createCell(row, columnCount++, "PM", style);
            createCell(row, columnCount++, "Phase", style);
            createCell(row, columnCount++, "Start Date", style);
            createCell(row, columnCount++, "End Date", style);
            createCell(row, columnCount++, "Week Ending Date", style);
            createCell(row, columnCount++, "Schedule", style);
            createCell(row, columnCount++, "CSAT", style);
            createCell(row, columnCount++, "Budget", style);
            createCell(row, columnCount++, "Engagement Risk", style);
            createCell(row, columnCount++, "Resources", style);
            createCell(row, columnCount++, "Status Indicator", style);
            createCell(row, columnCount++, "Delivery Model", style);
            createCell(row, columnCount, "Practice", style);

            writePMLCScorecardReportLines(workbook, sheet,opcoId);


            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
//            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }

    }

    /**
     * This service would be used to creating Excel file for Project Archive Data.
     * @param outputStream is use to write the Project Archive Data into the excel file.
     * @throws ActionFailureException exception would be throws, if Project Archive Data not found.
     * @throws IOException exception would be throws, if outputStream data not found.
     */
    public void createProjectArchiveData(OutputStream outputStream) throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("Sheet");
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setShrinkToFit(true);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);
            // headings
            createCell(row, columnCount++, "Project_ID (For Internal Reference Only)", style);
            createCell(row, columnCount++, "Project_Name", style);
            createCell(row, columnCount++, "Client_Name", style);
            createCell(row, columnCount++, "GDP_ID", style);
            createCell(row, columnCount++, "Opportunity_ID", style);
            createCell(row, columnCount++, "Business_Unit", style);
            createCell(row, columnCount++, "Delivery_Model", style);
            createCell(row, columnCount++, "Delivery_Organization", style);
            createCell(row, columnCount++, "Sales_Region", style);
            createCell(row, columnCount++, "Practice_Engagement", style);
            createCell(row, columnCount++, "Delivery_Lead", style);
            createCell(row, columnCount++, "Global_Delivery_Manager", style);
            createCell(row, columnCount++, "Engagement_Manager", style);
            createCell(row, columnCount++, "Program_Manager", style);
            createCell(row, columnCount++, "Secondary_DMORPM", style);
            createCell(row, columnCount++, "Practice_Lead", style);
            createCell(row, columnCount++, "Practice_Manager", style);
            createCell(row, columnCount++, "BDMORAM", style);
            createCell(row, columnCount++, "Solution_Engineer", style);
            createCell(row, columnCount++, "National_Account_Owner", style);
            createCell(row, columnCount++, "Project_Phase", style);
            createCell(row, columnCount++, "Start_Date", style);
            createCell(row, columnCount++, "End_Date", style);
            createCell(row, columnCount, "SMP_Site", style);

            writeProjectArchiveDateLines(workbook, sheet);

            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * This method would be use for writing the Manage Access Excel file.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     * @param isActive is the boolean value which check the project is active or not.
     */
    private void writeManageAccessExcel(SXSSFWorkbook workbook, Sheet sheet,boolean isActive,String opcoId) {
        List<Object[]> resourceList = new ArrayList();

        StringBuilder query=new StringBuilder();

        query.append(Queries.ADMIN_MANAGE_ACCESS_GET_QUERY);
        query.append(Constants.WHERE_CLAUSE+Constants.R_ACTIVE);
        query.append(isActive?Constants.VALUE_FOR_IS_ACTIVE:Constants.VALUE_FOR_IS_NOT_ACTIVE);
        query.append(Constants.SPACE+Constants.AND_CLAUS_ROPCOID);
        query.append(opcoId);
        query.append(Constants.ORDER_BY_EMPLOYEE);

        Query finalquery = entityManager.createNativeQuery(query.toString());

        resourceList = finalquery.getResultList();

        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        for (Object[] objects : resourceList) {
            AdminManageAccessDto manageAccessDto = AdminTabMapper.getAdminManageAccessDto(objects);
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, manageAccessDto.getEmployeeName()==null?"":manageAccessDto.getEmployeeName().trim(), style); // Employee Name
            createCell(row, columnCount++, manageAccessDto.getEmployeeId(), style); // Employee Id
            createCell(row, columnCount++, manageAccessDto.getUsername(), style); // User Name
            createCell(row, columnCount++, manageAccessDto.getJobTitle(), style); // Title
            createCell(row, columnCount++, manageAccessDto.getSupervisor()==null?"":manageAccessDto.getSupervisor().trim(), style); // Supervisor Name
            createCell(row, columnCount++, manageAccessDto.getLocation(), style); // Location
            if (manageAccessDto.getHireDate() != null) {
                createDateCell(row, columnCount++, Utils.getFormattedDate(manageAccessDto.getHireDate()), dateStyle);
            } else {
                createCell(row, columnCount++, "", style);    // Hire Date
            }
            if (!isActive) {
                if (manageAccessDto.getTerminationDate() != null) {
                    createDateCell(row, columnCount++, Utils.getFormattedDate(manageAccessDto.getTerminationDate()),
                            dateStyle); // Termination Date
                } else {
                    createCell(row, columnCount++, "", style); // Termination Date
                }
            }
            createCell(row, columnCount++, manageAccessDto.getAccess(), style); // Access
        }
    }

    /**
     * This method would be use for writing the DataLines Excel file.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     * @param tableName is the name of Excel file.
     */
    private void writeDataLines(SXSSFWorkbook workbook, Sheet sheet, String tableName,Boolean active,Integer opcoId) {
        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<Object[]> list = new ArrayList<>();
        Query finalQuery=null;
        if(tableName.equals("Client")) {
            if(active==false) {
                finalQuery = entityManager.createNativeQuery(getQuery(tableName) + " WHERE opcoId=" + opcoId + " AND Active=" + Constants.FALSE);
            }
            else{
                finalQuery = entityManager.createNativeQuery(getQuery(tableName) + " WHERE opcoId=" + opcoId + " AND Active=" + Constants.TRUE);
            }
        }else if (tableName.equals("DeliveryOrganizationType")){
            if(active==false) {
                finalQuery = entityManager.createNativeQuery(getQuery(tableName) + " WHERE Active=" + Constants.FALSE);
            }
            else{
                finalQuery = entityManager.createNativeQuery(getQuery(tableName) + " WHERE Active=" + Constants.TRUE);
            }
        }else{
            finalQuery = entityManager.createNativeQuery(getQuery(tableName));
        }

        list.addAll(finalQuery.getResultList());

        for (Object[] objects : list) {
            AdminExportToExcelDto dataModel = AdminExportToExcelDto.mapAdminExportToExcelDto(objects);
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, dataModel.getName(), style);
            createCell(row, columnCount++, dataModel.getPreviousName(), style);
            createCell(row, columnCount++, dataModel.getAction(), style);
            // using name of Creator if LastModifiedByUser is empty
            String lastUpdatedBy = dataModel.getModifiedBy().isEmpty() ? dataModel.getCreatedBy()
                    : dataModel.getModifiedBy();
            createCell(row, columnCount++, lastUpdatedBy, style);
            createDateCell(row, columnCount++, Utils.getFormattedDate(dataModel.getModifiedAt()), dateStyle);
        }
    }

    /**
     * This method would be use for writing the Report Data Lines Excel file.
     *
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet    is the Sheet data.
     * @param isActive is the boolean value which check the project is active or not.
     * @param opcoID
     */
    private void writeReportDataLines(SXSSFWorkbook workbook, Sheet sheet, boolean isActive, String opcoID) {
        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) (short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder reportDataExcelQuery = new StringBuilder(Queries.REPORT_DATA_EXCEL_QUERY);

        if(isActive) {
            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            reportDataExcelQuery.append(Constants.ENGAGEMENT_STATUS);
            reportDataExcelQuery.append(1);
            reportDataExcelQuery.append(Constants.AND_CLAUSE);
            reportDataExcelQuery.append(Constants.OPCO_STATUS);
            reportDataExcelQuery.append(opcoID);
        }else {
            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            reportDataExcelQuery.append(Constants.OPCO_STATUS);
            reportDataExcelQuery.append(opcoID);
        }

//        if (Constants.USER.get().getRoles().getRole().equals("Executive") && isActive){
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
//            reportDataExcelQuery.append(Constants.ENGAGEMENT_STATUS);
//            reportDataExcelQuery.append(1);
//            reportDataExcelQuery.append(Constants.AND_CLAUSE);
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoID);
//        }
//        if(Constants.USER.get().getRoles().getRole().equals("Executive") &&!isActive){
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoID);
//        }
//        if (Constants.USER.get().getRoles().getRole().equals("Admin") && isActive) {
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoID);
//            reportDataExcelQuery.append(Constants.AND_CLAUSE);
//            reportDataExcelQuery.append(Constants.ENGAGEMENT_STATUS);
//            reportDataExcelQuery.append(1);
//        }
//        if(Constants.USER.get().getRoles().getRole().equals("Admin") &&!isActive){
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoID);
//        }
//        if ((Constants.USER.get().getRoles().getRole().equals("Global View Access")
//                || Constants.USER.get().getRoles().getRole().equals("Global Edit Access"))
//                && isActive){
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
//            reportDataExcelQuery.append(Constants.ENGAGEMENT_STATUS);
//            reportDataExcelQuery.append(1);
//            reportDataExcelQuery.append(Constants.AND_CLAUSE);
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoID);
//        }
//        if( (Constants.USER.get().getRoles().getRole().equals("Global View Access")
//                || Constants.USER.get().getRoles().getRole().equals("Global Edit Access"))
//               &&!isActive){
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoID);
//        }

        Query finalQuery = entityManager.createNativeQuery(reportDataExcelQuery.toString());
        list.addAll(finalQuery.getResultList());

        for (Object[] objects : list) {
            ProjectDataDto projectDataDto = ProjectDataDto.projectDataMapper(objects);

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, projectDataDto.getProject(), style);
            createCell(row, columnCount++, projectDataDto.getAccountName(), style);
            createCell(row, columnCount++, projectDataDto.getGdpId(), style);
            createCell(row, columnCount++, projectDataDto.getPeopleSoftId(), style);
            createCell(row, columnCount++, projectDataDto.getPeoplesoftEngagementId(), style);
            createCell(row, columnCount++, projectDataDto.getDeliveryModel(), style);
            if(!"2".equals(opcoID)) {
                createCell(row, columnCount++, projectDataDto.getPracticeEngagement(), style);
            }
            createCell(row, columnCount++, projectDataDto.getLocationOfServices(), style);
            createCell(row, columnCount++, projectDataDto.getOpportunityId(), style);
            createCell(row, columnCount++, projectDataDto.getSMPLink(), style);
            createCell(row, columnCount++, projectDataDto.getBusinessUnit(), style);
            createCell(row, columnCount++, projectDataDto.getServiceType(), style);
            createCell(row, columnCount++, projectDataDto.getGDD()==null?"":projectDataDto.getGDD().trim(), style);
            createCell(row, columnCount++, projectDataDto.getGDM()==null?"":projectDataDto.getGDM().trim(), style);
            createCell(row, columnCount++, projectDataDto.getPM()==null?"":projectDataDto.getPM().trim(), style);
            createCell(row, columnCount++, projectDataDto.getCSL()==null?"":projectDataDto.getCSL().trim(), style);
            createCell(row, columnCount++, projectDataDto.getBDM()==null?"":projectDataDto.getBDM().trim(), style);
            createCell(row, columnCount++, projectDataDto.getNationalAccountOwner()==null?"":projectDataDto.getNationalAccountOwner().trim(), style);
            createCell(row, columnCount++, projectDataDto.getOSGPOA()==null?"":projectDataDto.getOSGPOA().trim(), style);
            createCell(row, columnCount++, projectDataDto.getOSGBOA()==null?"":projectDataDto.getOSGBOA().trim(), style);
            createCell(row, columnCount++, projectDataDto.getSalesRegion(), style); //sales region is replaced with sales organization in query
//            createCell(row, columnCount++, projectDataDto.getOpportunityOwner(), style); removed
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(projectDataDto.getStartDate()), dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(projectDataDto.getEndDate()), dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, projectDataDto.getProjectStage(), style);
            try {
                if (projectDataDto.getDummyRecord()) {
                    createCell(row, columnCount++, "", style);
                } else {
                    createDateCell(row, columnCount++, Utils.getFormattedDate(projectDataDto.getStatusDate()), dateStyle);
                }
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, projectDataDto.getSummary(), style);
            createCell(row, columnCount++, projectDataDto.getSchedule(), style);
            createCell(row, columnCount++, projectDataDto.getScheduleComments(), style);
            createCell(row, columnCount++, projectDataDto.getCSAT(), style);
            createCell(row, columnCount++, projectDataDto.getCSATComments(), style);
            createCell(row, columnCount++, projectDataDto.getBudget(), style);
            createCell(row, columnCount++, projectDataDto.getBudgetComments(), style);
            createCell(row, columnCount++, projectDataDto.getProjectRisk(), style);
            createCell(row, columnCount++, projectDataDto.getProjectRiskComments(), style);
            createCell(row, columnCount++, projectDataDto.getResources(), style);
            createCell(row, columnCount++, projectDataDto.getResourcesComments(), style);
            createCell(row, columnCount++, projectDataDto.getOverallStatus(), style);
            if( !"2".equals(opcoID)) {
                createCell(row, columnCount++, Utils.getRiskIndicatorValueFromColor(projectDataDto.getRiskProfileIndicator()), style);

                try {
                    createDateCell(row, columnCount++, Utils.getFormattedDate(projectDataDto.getRiskSurveyDate()),
                            dateStyle);
                } catch (Exception ex) {
                    createCell(row, columnCount, "", style);
                }

                try {
                    createDateCell(row, columnCount++, Utils.getFormattedDate(projectDataDto.getSecurityStatusDate()),
                            dateStyle);
                } catch (Exception ex) {
                    createCell(row, columnCount, "", style);
                }
            }
            if (!isActive)
                createCell(row, columnCount++, projectDataDto.isActive() ? Constants.ACTIVE : Constants.CLOSED,
                        style);
            createCell(row, columnCount++, projectDataDto.getTargetTechnologyPlatform(), style);
        }
    }



    /**
     * This method would be use for writing the Milestone Delivery Report Lines Excel file.
     *
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet    is the Sheet data.
     * @param isActive is the boolean value which check the project is active or not.
     */
    private void writeMilestoneDeliveryReportLines(SXSSFWorkbook workbook, Sheet sheet, boolean isActive,String opcoId) {
        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) (short) 11);
        style.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder milestoneReportQuery = new StringBuilder(Queries.MILESTONE_DELIVERY_REPORT_EXCEL_QUERY);

        if (isActive) {
            milestoneReportQuery.append(Constants.DELIVERY_MILESTONE_ONLY_ACTIVE_STATUS);
            milestoneReportQuery.append(" WHERE P.Active = 1 ");
            milestoneReportQuery.append(Constants.AND_CLAUSE);
            milestoneReportQuery.append(Constants.OPCO_STATUS);
            milestoneReportQuery.append(opcoId);
        }

        else{
            milestoneReportQuery.append(Constants.WHERE_CLAUSE);
            milestoneReportQuery.append(Constants.OPCO_STATUS);
            milestoneReportQuery.append(opcoId);
        }
        Query finalQuery = entityManager.createNativeQuery(milestoneReportQuery.toString());
        list.addAll(finalQuery.getResultList());

        for (Object[] objects : list) {
            MilestoneDeliveryReportDto milestoneDeliveryDto = MilestoneDeliveryReportDto
                    .milestoneDeliveryReportDtoMapper(objects);

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, milestoneDeliveryDto.getProject(), style);
            createCell(row, columnCount++, milestoneDeliveryDto.getAccountName(), style);
            createCell(row, columnCount++, milestoneDeliveryDto.getGDD(), style);
            createCell(row, columnCount++, milestoneDeliveryDto.getGDPId(), style);
            createCell(row, columnCount++, milestoneDeliveryDto.getDeliveryType(), style);
            createCell(row, columnCount++, milestoneDeliveryDto.getMilestoneDescription(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(milestoneDeliveryDto.getStartDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(milestoneDeliveryDto.getFinishDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, milestoneDeliveryDto.getComments(), style);
            if (!isActive)
                createCell(row, columnCount, milestoneDeliveryDto.isStatus(), style);
        }
    }

    /**
     * This method would be use for writing the Milestone Agile Sprint Data Lines.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     * @param isActive is the boolean value which check the project is active or not.
     */
    private void writeMilestoneAgileSprintDataLines(SXSSFWorkbook workbook, Sheet sheet, boolean isActive,String opcoId) {
        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder reportDataExcelQuery = new StringBuilder(Queries.MILESTONE_AGILE_SPRINT_REPORT_EXCEL_QUERY);
        if (isActive) {
            reportDataExcelQuery.append(Constants.AGILE_MILESTONE_ONLY_ACTIVE_STATUS);
            reportDataExcelQuery.append(" WHERE P.Active = 1 ");
            reportDataExcelQuery.append(Constants.AND_CLAUSE);
            reportDataExcelQuery.append(Constants.OPCO_STATUS);
            reportDataExcelQuery.append(opcoId);
        }
        else{
            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            reportDataExcelQuery.append(Constants.OPCO_STATUS);
            reportDataExcelQuery.append(opcoId);
        }

        Query finalQuery = entityManager.createNativeQuery(reportDataExcelQuery.toString());
        list.addAll(finalQuery.getResultList());

        for (Object[] objects : list) {
            MilestoneAgileSprintReportDto agileSprintReportDto = MilestoneAgileSprintReportDto
                    .milestoneAgileSprintReportDtoMapper(objects);

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, agileSprintReportDto.getProject(), style);
            createCell(row, columnCount++, agileSprintReportDto.getAccountName(), style);
            createCell(row, columnCount++, agileSprintReportDto.getGDD(), style);
            createCell(row, columnCount++, agileSprintReportDto.getGDPId(), style);
            createCell(row, columnCount++, agileSprintReportDto.getTrack(), style);
            createCell(row, columnCount++, agileSprintReportDto.getSprint(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(agileSprintReportDto.getStartDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(agileSprintReportDto.getFinishDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, agileSprintReportDto.getDeliveredStories(), style);
            createCell(row, columnCount++, agileSprintReportDto.getCommittedStoryPoints(), style);
            createCell(row, columnCount++, agileSprintReportDto.getDeliveredStoryPoints(), style);
            createCell(row, columnCount++, agileSprintReportDto.getAddedStories(), style);
            createCell(row, columnCount++, agileSprintReportDto.getRemovedStories(), style);
            createCell(row, columnCount++, agileSprintReportDto.getCapacity(), style);
            createCell(row, columnCount++, agileSprintReportDto.getEstimate(), style);
            createCell(row, columnCount++, agileSprintReportDto.getGoalMet(), style);
            createCell(row, columnCount++, agileSprintReportDto.getSprintStatus(), style);
            if (!isActive)
                createCell(row, columnCount,
                        agileSprintReportDto.isStatus() ? Constants.ACTIVE : Constants.CLOSED, style);
        }
    }

    /**
     * This method would be use for writing the Security Profile Report Data Lines.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     * @param isActive is the boolean value which check the project is active or not.
     */
    private void writeSecurityProfileReportDataLines(SXSSFWorkbook workbook, Sheet sheet, boolean isActive,String opcoId) {
        int rowCount = 2;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder reportDataExcelQuery = new StringBuilder(Queries.TGS_IS_SECURITY_PROFILE_REPORT_QUERY);
        if (isActive){
            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            reportDataExcelQuery.append(Constants.ENGAGEMENT_STATUS);
            reportDataExcelQuery.append(1);
            reportDataExcelQuery.append(Constants.AND_CLAUSE);
            reportDataExcelQuery.append(Constants.OPCO_STATUS);
            reportDataExcelQuery.append(opcoId);
        }else{
            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            reportDataExcelQuery.append(Constants.OPCO_STATUS);
            reportDataExcelQuery.append(opcoId);
        }

//        if(!isActive){
//            reportDataExcelQuery.append(Constants.WHERE_CLAUSE);
////            reportDataExcelQuery.append(Constants.OPCO_STATUS);
////            reportDataExcelQuery.append(gdpUserDetailsService.getUserOpcoId());
//
//            reportDataExcelQuery.append(Constants.OPCO_STATUS);
//            reportDataExcelQuery.append(opcoId);
//        }
        Query finalQuery = entityManager.createNativeQuery(reportDataExcelQuery.toString());
        list.addAll(finalQuery.getResultList());

//        for(Object[] a:list) {
//            for(Object b: a) {
//                System.out.println("excel objects" + b);
//            }
//        }


        for (Object[] objects : list) {

            TGSSecurityReportDto securityReportDto = TGSSecurityReportDto.tgsSecurityReportDtoMapper(objects);

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, securityReportDto.getProjectName(), style);

            createCell(row, columnCount++, securityReportDto.getAccountName(), style);
            createCell(row, columnCount++, securityReportDto.getGDD()==null?"":securityReportDto.getGDD().trim(), style);
            createCell(row, columnCount++, securityReportDto.getCSL()==null?"":securityReportDto.getCSL().trim(), style);
            createCell(row, columnCount++, securityReportDto.getPractice(), style);
            createCell(row, columnCount++, securityReportDto.getLocation(), style);
            createCell(row, columnCount++, securityReportDto.getSMPLink(), style);
            createCell(row, columnCount++, securityReportDto.getOpportunityId(), style);
            createCell(row, columnCount++, securityReportDto.getGDPId(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(securityReportDto.getProjectEndDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, securityReportDto.getAnswer1(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer2(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer3(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer4(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer5(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer6(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer7(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer8(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer9(), style);
            createCell(row, columnCount++, securityReportDto.getAnswer10(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(securityReportDto.getModifiedAt()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount, securityReportDto.getModifiedBy(), style);
        }
    }

    /**
     * This method would be use for writing the PMLCScorecard Report Lines.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     */
    private void writePMLCScorecardReportLines(SXSSFWorkbook workbook, Sheet sheet,String opcoId) {
        int rowCount = 1;

        createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder reportDataExcelQuery = new StringBuilder(Queries.PMLC_SCORECARD_REPORT_QUERY);

        reportDataExcelQuery.append("");

        reportDataExcelQuery.append(Constants.AND_CLAUSE);
        reportDataExcelQuery.append(Constants.OPCO_STATUS);
        reportDataExcelQuery.append(opcoId);

        Query finalQuery = entityManager.createNativeQuery(reportDataExcelQuery.toString());
        list.addAll(finalQuery.getResultList());

        for (Object[] objects : list) {
            PMLCScorecardReportDto scorecardReportDto = PMLCScorecardReportDto
                    .scorecardReportDtoMapper(objects);

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, scorecardReportDto.getAccountName(), style);
            createCell(row, columnCount++, scorecardReportDto.getProjectName(), style);
            createCell(row, columnCount++, scorecardReportDto.getGDPId(), style);
            createCell(row, columnCount++, Utils.getRiskIndicatorValueFromColor(scorecardReportDto.getRiskProfile()), style);
            createCell(row, columnCount++, scorecardReportDto.getSMPLink(), style);
            createCell(row, columnCount++, scorecardReportDto.getGDD()==null?"":scorecardReportDto.getGDD().trim(), style);
            createCell(row, columnCount++, scorecardReportDto.getGDM()==null?"":scorecardReportDto.getGDM().trim(), style);
            createCell(row, columnCount++, scorecardReportDto.getCSL()==null?"":scorecardReportDto.getCSL().trim(), style);
//            createCell(row, columnCount++, scorecardReportDto.getPM(), style);
            createCell(row, columnCount++, scorecardReportDto.getPhase(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(scorecardReportDto.getStartDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(scorecardReportDto.getEndDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                if (scorecardReportDto.getDummyRecord()) {
                    createCell(row, columnCount++, "", style);
                } else {
                    createDateCell(row, columnCount++, Utils.getFormattedDate(scorecardReportDto.getWeekEndingDate()), dateStyle);
                }
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount++, scorecardReportDto.getSchedule(), style);
            createCell(row, columnCount++, scorecardReportDto.getCSAT(), style);
            createCell(row, columnCount++, scorecardReportDto.getBudget(), style);
            createCell(row, columnCount++, scorecardReportDto.getProjectRisk(), style);
            createCell(row, columnCount++, scorecardReportDto.getResources(), style);
            createCell(row, columnCount++, scorecardReportDto.getOverallStatus(), style);
            createCell(row, columnCount++, scorecardReportDto.getDeliveryModel(), style);
            createCell(row, columnCount, scorecardReportDto.getPracticeEngagement(), style);
        }
    }

    /**
     * This method would be use for writing the Project Archive Date Lines.
     * @param workbook is the SXSSFWorkbook data.
     * @param sheet is the Sheet data.
     */
    private void writeProjectArchiveDateLines(SXSSFWorkbook workbook, Sheet sheet) {
        int rowCount = 1;

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        dateStyle.setFont(font);

        List<GDPArchivedPracticeDetails> list = archivedPracticeDetailsRepository.findAll();
        for (GDPArchivedPracticeDetails archive : list) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, archive.getProjectId(), style);
            createCell(row, columnCount++, archive.getProjectName(), style);
            createCell(row, columnCount++, archive.getClientName(), style);
            createCell(row, columnCount++, archive.getGdpId(), style);
            createCell(row, columnCount++, archive.getOpportunityId(), style);
            createCell(row, columnCount++, archive.getBusinessUnit(), style);
            createCell(row, columnCount++, archive.getDeliveryModel(), style);
            createCell(row, columnCount++, archive.getDeliveryOrganization(), style);
            createCell(row, columnCount++, archive.getSalesRegion(), style);
            createCell(row, columnCount++, archive.getPracticeEngagement(), style);
            createCell(row, columnCount++, archive.getDeliveryLead(), style);
            createCell(row, columnCount++, archive.getGlobalDeliveryManager(), style);
            createCell(row, columnCount++, archive.getCustomerSuccessLeader(), style);
            createCell(row, columnCount++, archive.getProjectManager(), style);
            createCell(row, columnCount++, archive.getSecondaryDMORPM(), style);
            createCell(row, columnCount++, archive.getPracticeLead(), style);
            createCell(row, columnCount++, archive.getPracticeManager(), style);
            createCell(row, columnCount++, archive.getBdmORAM(), style);
            createCell(row, columnCount++, archive.getSolutionEngineer(), style);
            createCell(row, columnCount++, archive.getNationalAccountOwner(), style);
            createCell(row, columnCount++, archive.getProjectPhase(), style);
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(archive.getStartDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            try {
                createDateCell(row, columnCount++, Utils.getFormattedDate(archive.getEndDate()),
                        dateStyle);
            } catch (Exception e) {
                createCell(row, columnCount, "", style);
            }
            createCell(row, columnCount, archive.getSmpSite(), style);
        }
    }

    public void createTrendReportData(OutputStream outputStream, boolean isActive, Date fromWeekEndDate, Date nextWeekEndDate, String opcoId)
            throws ActionFailureException, IOException {
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            Sheet sheet;
            sheet = workbook.createSheet("Trend Report");

            // creating headline
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setFontHeightInPoints((short) (short) 11);
            style.setFont(font);
            int columnCount = 0;
            sheet.setDefaultColumnWidth(30);

            createCell(row, columnCount++, "Practice", style);
            createCell(row, columnCount++, "PeopleSoft Project ID", style);
            createCell(row, columnCount++, "PeopleSoft Engagement ID", style);
            createCell(row, columnCount++, "GDP ID", style);
            createCell(row, columnCount++, "Engagement Name", style);
            createCell(row, columnCount++, "Account Name", style);
            createCell(row, columnCount++, "GDD", style);
            createCell(row, columnCount++, "GDM", style);
            createCell(row, columnCount++, "EM", style);
            createCell(row, columnCount++, "BDM", style);
            createCell(row, columnCount++, "Risk Profile", style);
            createCell(row, columnCount++, "CurrentPhase", style);
            createCell(row, columnCount++, "OverAllStatus", style);
            createCell(row, columnCount++, "WeekEndingDate", style);
            createCell(row, columnCount++, "Summary", style);

            writeTrendReportDataLines(workbook, sheet, isActive, fromWeekEndDate, nextWeekEndDate,opcoId);

            workbook.write(outputStream);


        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            outputStream.flush();
            outputStream.close();
        }


    }

    private void writeTrendReportDataLines(SXSSFWorkbook workbook, Sheet sheet, boolean isActive, Date fromWeekEndDate,Date nextWeekEndDate,String opcoId) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) (short) 11);
        style.setFont(font);

        List<Object[]> list = new ArrayList<>();
        StringBuilder trendReportDataExcelQuery = new StringBuilder(Queries.TREND_REPORT_DATA_EXCEL_QUERY);
        if (isActive){
            trendReportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            trendReportDataExcelQuery.append(Constants.OPCO_STATUS);
            trendReportDataExcelQuery.append(opcoId);
            trendReportDataExcelQuery.append(Constants.AND_CLAUSE);
            trendReportDataExcelQuery.append(Constants.ENGAGEMENT_STATUS);
            trendReportDataExcelQuery.append(1);

        }
        else  {
            trendReportDataExcelQuery.append(Constants.WHERE_CLAUSE);
            trendReportDataExcelQuery.append(Constants.OPCO_STATUS);
            trendReportDataExcelQuery.append(opcoId);
        }
//
        Query finalQuery = entityManager.createNativeQuery(trendReportDataExcelQuery.toString());
        finalQuery.setParameter(1,fromWeekEndDate);
        finalQuery.setParameter(2,nextWeekEndDate);
        list.addAll(finalQuery.getResultList());

        int columnCount;

        if(list != null) {
            for (Object[] objects : list) {

                columnCount = 0;
                TrendDto trendsDto = TrendDto.trendMapper(objects);
                Row row = sheet.createRow(rowCount++);

                createCell(row, columnCount++, trendsDto.getPractice(), style);
                createCell(row, columnCount++, trendsDto.getPeopleSoftProjectId(), style);
                createCell(row, columnCount++, trendsDto.getPeopleSoftEngagementId(), style);
                createCell(row, columnCount++, trendsDto.getGDPId(), style);
                createCell(row, columnCount++, trendsDto.getProject(), style);
                createCell(row, columnCount++, trendsDto.getAccountName(), style);
                createCell(row, columnCount++, ObjectUtils.isEmpty(trendsDto.getGDD()) ? "" : trendsDto.getGDD().trim(), style);
                createCell(row, columnCount++, ObjectUtils.isEmpty(trendsDto.getGDM()) ? "" : trendsDto.getGDM().trim(), style);
                createCell(row, columnCount++, ObjectUtils.isEmpty(trendsDto.getCSL()) ? "" : trendsDto.getCSL().trim(), style);
                createCell(row, columnCount++, ObjectUtils.isEmpty(trendsDto.getBDM()) ? "" : trendsDto.getBDM().trim(), style);
                createCell(row, columnCount++, Utils.getRiskIndicatorValueFromColor(trendsDto.getRiskProfile()), style);
                createCell(row, columnCount++, trendsDto.getCurrentPhase(), style);
                createCell(row, columnCount++, trendsDto.getOverAllStatus(), style);
                createCell(row, columnCount++, trendsDto.getWeekEndingDate(), style);
                createCell(row, columnCount++, trendsDto.getSummary(), style);

            }
        }

    }



    /**
     * This method would be use for creating Cell of Excel files.
     * @param row is the excel Row data.
     * @param columnCount is the excel column count data.
     * @param value is the excel Cell value.
     * @param style is the excel style data.
     */
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        String text = value == null ? "" : String.valueOf(value);
        Cell cell = row.createCell(columnCount);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    /**
     * This method would be use for creating Datetype Cell of Excel files.
     * @param row is the excel Row data.
     * @param columnCount is the excel column count data.
     * @param value is the excel Cell value.
     * @param dateStyle is the excel style data.
     */
    private void createDateCell(Row row, int columnCount, Object value, CellStyle dateStyle) {
        String text = value == null ? "" : String.valueOf(value);
        Date date = new Date();
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(text);
        } catch (ParseException e) {
            try{
                date = new SimpleDateFormat("yyyy-MM-dd").parse(text);
            }catch (ParseException ex){
                ex.printStackTrace();
            }
        }
        Cell cell = row.createCell(columnCount);
        cell.setCellValue(date);
        cell.setCellStyle(dateStyle);
    }

    /**
     * This method would be use for getting Excel files data using Table Name.
     * @param tableName is the Excel files name.
     * @return this method would be return Excel files data.
     */
    private String getQuery(String tableName) {
        return String.format(Queries.ADMIN_FETCH_EXCEL_DATA_QUERY, tableName, tableName);
    }

}

