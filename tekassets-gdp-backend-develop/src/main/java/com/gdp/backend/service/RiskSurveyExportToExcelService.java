package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.DeliveryRiskIndicator;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This Service class used for RiskSurvey Export to Excel SecurityProfile.
 * @author gdp
 *
 */
@Service
public class RiskSurveyExportToExcelService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CreateProjectManuallyService createProjectManuallyService;

    @Autowired
    RiskSurveyService riskSurveyService;

    @Value("${excelFilePathForRiskSurvey}")
    private String excelFilePathForRiskSurvey;

    @Value("${excelFilePathForSecurityProfile}")
    private String excelFilePathForSecurityProfile;

    /**
     * This service would be use for getting Security Profile related data into the excel file using projectId.
     * @param projectId is the id of existing Project.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @param source is the Project source.
     * @return this method would be return list of data which found as per search.
     * @throws ActionFailureException exception would be throws if Security Profile data not found.
     */
    public ByteArrayInputStream exportToExcelSecurityProfile(Integer projectId, HttpServletResponse httpServletResponse
            , String source) throws ActionFailureException {
        return exportToExcelExistingFile(projectId, null, httpServletResponse, source);
    }

    /**
     * This service would be use for getting Security Profile related data into the excel file using projectId.
     * @param projectId is the id of existing Project.
     * @param projectClientNameDto is the project client Name data.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @param source is the Project source.
     * @return this method would be return list of data which found as per search.
     * @throws ActionFailureException exception would be throws if Security Profile data not found.
     */
    public ByteArrayInputStream exportToExcelExistingFile(Integer projectId, ProjectClientNameDto projectClientNameDto,
                                                          HttpServletResponse httpServletResponse, String source) throws ActionFailureException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            InputStream inputStream = new ClassPathResource((getExcelFilePath(source))).getInputStream();
            File file = ResourceUtils.getFile("classpath:"+getExcelFilePath(source));
            FileInputStream inputStream = new FileInputStream(file);
            try (Workbook workbook = WorkbookFactory.create(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);
                if ((Constants.RISK_SURVEY).equals(source)) {
                    Row row = sheet.createRow(1);
                    row.createCell(0).setCellValue(projectClientNameDto.getProjectName());
                    row.createCell(1).setCellValue(projectClientNameDto.getAccountName());
                    exportToExcelRiskSurvey(sheet, projectId, row);
                } else if ((Constants.SECURITY_PROFILE).equals(source) && projectId != -1) {
                    Row row = sheet.createRow(1);
                    writeSecurityProfileExcel(sheet, projectId, row);
                }
                if (!isEmpty(inputStream)) {
                    inputStream.close();
                }
                workbook.write(outputStream);
            }
            outputStream.close();
            httpServletResponse.flushBuffer();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            throw new ActionFailureException(EStatusCode.ERROR_WRITING_FILE.name(), ex);
        }
    }

    /**
     * This method would be use for getting the Path of project Excel File.
     * @param source is the source file name.
     * @return this method would be return path of the Excel File.
     */
    public String getExcelFilePath(String source) {
        String path = null;
        if (Constants.RISK_SURVEY.equals(source)) {
            path = excelFilePathForRiskSurvey;
        } else if (Constants.SECURITY_PROFILE.equals(source)) {
            path = excelFilePathForSecurityProfile;
        } else {
            path = null;
        }
        return path;
    }

    /**
     * This method would be use for exporting to Excel RiskSurvey.
     * @param sheet is the position of file.
     * @param projectId is the id of existing Project.
     * @param row is the Excel file row.
     */
    public void exportToExcelRiskSurvey(Sheet sheet, Integer projectId, Row row) {
        if (projectId != -1) {
            RiskSurveyDto riskSurveyDto = riskSurveyService.getRiskSurvey(projectId);
            if (!isEmpty(riskSurveyDto.getIsWarrantyApplicable())) {
                setRiskAttributes(riskSurveyDto, sheet, row);
            }
        }
    }

    /**
     * This method would be use for writing the SecurityProfileExcel.
     * @param sheet is the position of file.
     * @param projectId is the id of existing Project.
     * @param row is the Excel file row.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void writeSecurityProfileExcel(Sheet sheet, Integer projectId, Row row) throws ActionFailureException {
        writeSecurityProfileHeader(sheet, projectId, row);
        Query query = entityManager.createNativeQuery(Queries.SECURITY_PROFILE_ANSWERS_AND_COMMENTS_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> answerCommentList = query.getResultList();
        if (isEmpty(answerCommentList)) {
            return;
        }
        setSecurityProfileStatusAndComments(sheet, answerCommentList);

    }

    /**
     * This method would be use for writing the SecurityProfileHeader.
     * @param sheet is the position of file.
     * @param projectId is the id of existing Project.
     * @param row is the Excel file row.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void writeSecurityProfileHeader(Sheet sheet, Integer projectId, Row row) throws ActionFailureException {
        CreateProjectFormDto createProjectFormDto = createProjectManuallyService.findProjectById(projectId);
        row.createCell(0).setCellValue(createProjectFormDto.getProjectName());
        row.createCell(1).setCellValue(createProjectFormDto.getAccountName());
        row.createCell(3).setCellValue(createProjectFormDto.getDeliveryOrganizationName());
        row.createCell(4).setCellValue(createProjectFormDto.getLocationName());
        for (AddResourceDto resourceDto : createProjectFormDto.getManagerialInformation()) {
            if ((Constants.ENGAGEMENT_MANAGER).equals(resourceDto.getResourceRoleName())) {
                row.createCell(2).setCellValue(resourceDto.getResourceName());
                break;
            }
        }

        row = sheet.createRow(4);
        row.createCell(0).setCellValue(createProjectFormDto.getLinkToSMPSite());
        row.createCell(1).setCellValue(createProjectFormDto.getOpportunityId());
        row.createCell(2).setCellValue(createProjectFormDto.getGdpId());
        if (!isEmpty(createProjectFormDto.getEndDate())) {
            row.createCell(3).setCellValue(OpportunityDto.getFormattedDate(createProjectFormDto.getEndDate()));
        }
    }

    /**
     * This method would be use for writing the SecurityProfileStatus and Comments.
     * @param sheet is the position of file.
     * @param answerCommentList is the list of answerComment.
     */
    public void setSecurityProfileStatusAndComments(Sheet sheet, List<Object[]> answerCommentList) {
        Map<Integer, List<String>> answerMap = new LinkedHashMap<>();
        Map<Integer, String> commentsMap = new LinkedHashMap<>();
        for (Object[] answer : answerCommentList) {
            answerMap.put((Integer) answer[0], new ArrayList<>());
            commentsMap.put((Integer) answer[0], (String) answer[2]);
        }
        for (Object[] answer : answerCommentList) {
            answerMap.get(answer[0]).add(((String) answer[1]));
        }
        Cell cell;
        int rowNumber = 7;
        for (Map.Entry<Integer, List<String>> entry : answerMap.entrySet()) {
            cell = sheet.getRow(rowNumber).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String finalAnswer = "";
            if (entry.getValue().size() == 1) {
                finalAnswer = entry.getValue().get(0);
            } else {
                StringBuilder answerList = new StringBuilder();
                for (String answer : entry.getValue()) {
                    answerList.append(Constants.SEPARATOR);
                    answerList.append(answer);
                }
                finalAnswer = answerList.substring(1);
            }
            cell.setCellValue(finalAnswer);
            cell = sheet.getRow(rowNumber).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(commentsMap.get(entry.getKey()));
            rowNumber++;
        }
    }

    /**
     * This method would be use for getting the Delivery RiskIndicator data.
     * @param indicatorColor is the status indicator.
     * @return this method would be return risk indicator value.
     */
    public String getDeliveryRiskIndicator(String indicatorColor) {
        String riskIndicatorValue = null;
        if (("red").equals(indicatorColor)) {
            riskIndicatorValue = DeliveryRiskIndicator.HIGH.getMessage();
        } else if (("orange").equals(indicatorColor)) {
            riskIndicatorValue = DeliveryRiskIndicator.MODERATE.getMessage();
        } else if (("green").equals(indicatorColor)) {
            riskIndicatorValue = DeliveryRiskIndicator.LOW.getMessage();
        }
        return riskIndicatorValue;
    }


    /**
     * This method would be use for mapping the RiskAttributes data.
     * @param riskSurveyDto is the data mapped of RiskSurvey.
     * @param sheet is the position of file.
     * @param row is the Excel file row.
     */
    public void setRiskAttributes(RiskSurveyDto riskSurveyDto, Sheet sheet, Row row) {
        String indicatorColor = riskSurveyDto.getIndicatorColor();
        if (!isEmpty(indicatorColor)) {
            row.createCell(2).setCellValue(getDeliveryRiskIndicator(indicatorColor));
        }
        Cell cell;
        cell = sheet.getRow(4).getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(riskSurveyDto.getIsWarrantyApplicable());
        cell = sheet.getRow(5).getCell(2);
        cell.setCellValue(riskSurveyDto.getLiabilityLimits());
        cell = sheet.getRow(6).getCell(2);
        cell.setCellValue(riskSurveyDto.getContractType());
        cell = sheet.getRow(7).getCell(2);
        cell.setCellValue(riskSurveyDto.getCustomerCreditScore());
        cell = sheet.getRow(8).getCell(2);
        cell.setCellValue(riskSurveyDto.getSensitiveData());
        cell = sheet.getRow(9).getCell(2);
        cell.setCellValue(riskSurveyDto.getIsComplianceAssociated());
        cell = sheet.getRow(10).getCell(2);
        cell.setCellValue(riskSurveyDto.getHardwareAndEquipment());
        cell = sheet.getRow(11).getCell(2);
        cell.setCellValue(riskSurveyDto.getIsCovered());
        cell = sheet.getRow(12).getCell(2);
        cell.setCellValue(riskSurveyDto.getLocation());
        cell = sheet.getRow(13).getCell(2);
        cell.setCellValue(riskSurveyDto.getTypeOfService());
        cell = sheet.getRow(14).getCell(2);
        cell.setCellValue(riskSurveyDto.getContractualDuration());
        cell = sheet.getRow(15).getCell(2);
        cell.setCellValue(riskSurveyDto.getIsEstablishedAccount());
        cell = sheet.getRow(16).getCell(2);
        cell.setCellValue(riskSurveyDto.getTotalProjectedRevenue());
        cell = sheet.getRow(17).getCell(2);
        cell.setCellValue(riskSurveyDto.getEngagementWithTek());
        cell = sheet.getRow(18).getCell(2);
        cell.setCellValue(riskSurveyDto.getIs3rdPartyDependent());
        cell = sheet.getRow(19).getCell(2);
        cell.setCellValue(riskSurveyDto.getHasUniqueChallenges());
        cell = sheet.getRow(20).getCell(2);
        cell.setCellValue(riskSurveyDto.getHasHighRiskCategory());
        if (("Yes").equals(riskSurveyDto.getHasHighRiskCategory()) && !isEmpty(riskSurveyDto.getComments())) {
            cell = sheet.getRow(21).getCell(2);
            cell.setCellValue(riskSurveyDto.getComments());
        }

    }

}
