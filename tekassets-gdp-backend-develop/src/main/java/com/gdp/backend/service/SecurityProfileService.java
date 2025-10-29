package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.DeliveryRiskIndicator;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.enums.SecurityProfileStatus;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.mapper.SecurityProfileMapper;
import com.gdp.backend.model.Project;
import com.gdp.backend.model.ProjectSecurityProfile;
import com.gdp.backend.model.SecurityAnswer;
import com.gdp.backend.model.SecurityProfileAccessHistory;
import com.gdp.backend.repository.ProjectSecurityProfileRepository;
import com.gdp.backend.repository.ResourceRepository;
import com.gdp.backend.repository.SecurityProfileAccessHistoryRepository;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.Null;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This service class used for retrieving and saving the Security Profile related information and data.
 * @author gdp
 *
 */
@Service
public class SecurityProfileService {

    @Autowired
    EmailService emailService;

    @Autowired
    RiskSurveyService riskSurveyService;

    @Autowired
    ProjectStatusService projectStatusService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectSecurityProfileRepository projectSecurityProfileRepository;

    @Autowired
    SecurityProfileAccessHistoryRepository securityProfileAccessHistoryRepository;

    @Autowired
    ResourceRepository resourceRepository;

    /**
     * This service would be use for getting Security Profile related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return list of security Profile data.
     */
    public List<SecurityProfileDto> getSecurityProfileByProjectId(Integer projectId) {
        List<SecurityProfileDto> securityProfileDtoList = new ArrayList<>();
        Query query = entityManager.createNativeQuery(Queries.SECURITY_PROFILE_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> securityProfileList = query.getResultList();
        if (isEmpty(securityProfileList)) {
            return securityProfileDtoList;
        }
        Map<String, List<SecurityProfileAnswerDto>> securityProfileMap = new HashMap<>();
        Map<String, String> commentsMap = new HashMap<>();
        Map<String, Integer> positionMap = new HashMap<>();
        for (Object[] row : securityProfileList) {
            securityProfileMap.put((String) row[1], new ArrayList<>());

            if (row[2] != null) {
                String comment = (String) row[2];
                if (!comment.equals("null")) {
                    commentsMap.put((String) row[1], comment);
                }
            }
            positionMap.put((String) row[1], (Integer) row[0]);
        }
        for (Object[] row : securityProfileList) {
            securityProfileMap.get(row[1]).add(SecurityProfileAnswerDto.mapToSecurityProfileAnswerDto(row));
        }
        for (Map.Entry<String, List<SecurityProfileAnswerDto>> entry : securityProfileMap.entrySet()) {
            SecurityProfileDto securityProfileDto = new SecurityProfileDto();
            String securityQuestion = entry.getKey();
            securityProfileDto.setSecurityQuestion(securityQuestion);
            securityProfileDto.setSecurityAnswers(entry.getValue());
            securityProfileDto.setComments(commentsMap.get(securityQuestion));
            securityProfileDto.setPosition(positionMap.get(securityQuestion));
            securityProfileDtoList.add(securityProfileDto);
        }
        return securityProfileDtoList;
    }

    /**
     * This service would be use for getting Security Profile all answers related information and data.
     * @return this method would be return list of security Profile data.
     * @throws ActionFailureException exception would be throws if Security Profile data not found.
     */
    public List<SecurityProfileDto> getAllAnswersForSecurityProfile() throws ActionFailureException {
        List<SecurityProfileDto> securityProfileDtoList = new ArrayList<>();
        Query query = entityManager.createNativeQuery(Queries.ALL_ANSWERS_FOR_SECURITY_PROFILE);
        List<Object[]> answersList = query.getResultList();
        if (isEmpty(answersList)) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name());
        }
        Map<String, List<SecurityProfileAnswerDto>> securityProfileMap = new HashMap<>();
        Map<String, Integer> positionMap = new HashMap<>();
        Map<String, Boolean> multiSelectMap = new HashMap<>();
        for (Object[] row : answersList) {
            securityProfileMap.put((String) row[1], new ArrayList<>());
            positionMap.put((String) row[1], (Integer) row[0]);
            multiSelectMap.put((String) row[1], (Boolean) row[5]);
        }
        for (Object[] row : answersList) {
            securityProfileMap.get(row[1]).add(SecurityProfileAnswerDto.mapToSecurityProfileAnswerDtoAllAnswers(row));
        }
        for (Map.Entry<String, List<SecurityProfileAnswerDto>> entry : securityProfileMap.entrySet()) {
            SecurityProfileDto securityProfileDto = new SecurityProfileDto();
            String securityQuestion = entry.getKey();
            securityProfileDto.setSecurityQuestion(securityQuestion);
            securityProfileDto.setSecurityAnswers(entry.getValue());
            securityProfileDto.setMultiselect(multiSelectMap.get(securityQuestion));
            securityProfileDto.setPosition(positionMap.get(securityQuestion));
            securityProfileDtoList.add(securityProfileDto);
        }
        return securityProfileDtoList;
    }

    /**
     * This service would be use for getting Security Profile Report Log related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return list of security Profile Report Log data.
     */
    public List<SecurityProfileReportLog> getReportLogByProjectId(Integer projectId) {
        List<SecurityProfileReportLog> securityProfileReportLogList = new ArrayList<>();
        Query query = entityManager.createNativeQuery(Queries.SECURITY_PROFILE_REPORT_LOG_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> answersList = query.getResultList();
        int answerListLength = answersList.size();
        if (isEmpty(answersList)) {
            return securityProfileReportLogList;
        }
        Map<Date, List<SecuritySummaryDto>> modifiedAtMap = new HashMap<>();
        for (Object[] row : answersList) {
            modifiedAtMap.put((Date) row[3], new ArrayList<>());
        }
        for (Object[] row : answersList) {
            modifiedAtMap.get(row[3]).add(SecuritySummaryDto.mapToSecuritySummaryDto(row));
        }
        int index = 0;
        int k = 0;
        while (index < answerListLength) {
            SecurityProfileReportLog securityProfileReportLog = new SecurityProfileReportLog();
            securityProfileReportLog.setLastUpdatedBy((String) answersList.get(index)[4]);
            securityProfileReportLog.setDateAndTime((Date) answersList.get(index)[3]);
            securityProfileReportLog.setSummary(modifiedAtMap.get(answersList.get(index)[3]));
            k = modifiedAtMap.get(answersList.get(index)[3]).size();
            index = index + k;
            securityProfileReportLogList.add(securityProfileReportLog);
        }
        return securityProfileReportLogList;
    }


    /**
     * This service would be use for getting Security Profile Header related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return security Profile Header data.
     */
    public SecurityProfileHeaderDto getSecurityProfileHeaderByProjectId(Integer projectId) {
        SecurityProfileHeaderDto securityProfileHeaderDto = new SecurityProfileHeaderDto();
        Query query = entityManager.createNativeQuery(Queries.SECURITY_PROFILE_HEADER);
        query.setParameter(1, projectId);
        List<Object[]> securityHeaderList = query.getResultList();
        if (isEmpty(securityHeaderList)) {
            return securityProfileHeaderDto;
        }
        for (Object[] row : securityHeaderList) {
            if (isEmpty(securityProfileHeaderDto.getGlobalDeliveryManager()) ||
                    isEmpty(securityProfileHeaderDto.getCustomerSuccessLeader())) {
                securityProfileHeaderDto = mapValuesToSecurityProfileHeaderDto(row, securityProfileHeaderDto);
            }
        }
        securityProfileHeaderDto.setDeliveryRiskIndicator(getRiskIndicatorByProjectId(projectId));
        securityProfileHeaderDto.setSecurityProfileStatus(getSecurityProfileStatusByProjectId(projectId));
        return securityProfileHeaderDto;
    }

    /**
     * This method would be use for mapping the Values to Security Profile Header data.
     * @param columns is the Security Profile columns.
     * @param securityProfileHeaderDto is the security Profile Header request data.
     * @return this method would be return security Profile Header Data.
     */
    public SecurityProfileHeaderDto mapValuesToSecurityProfileHeaderDto(Object[] columns, SecurityProfileHeaderDto securityProfileHeaderDto) {
        String cslName;
        if (isEmpty(securityProfileHeaderDto.getProjectName()) || isEmpty(securityProfileHeaderDto.getAccountName())) {
            securityProfileHeaderDto.setProjectName((String) columns[4]);
            securityProfileHeaderDto.setAccountName((String) columns[5]);
        }
        if (("Global Delivery Manager").equals(columns[3])) {
            cslName = projectStatusService.getCslNameFromQuery(columns);
            securityProfileHeaderDto.setGlobalDeliveryManager(cslName);
        } else if (("Engagement Manager").equals(columns[3])) {
            cslName = projectStatusService.getCslNameFromQuery(columns);
            securityProfileHeaderDto.setCustomerSuccessLeader(cslName);
        }
        return securityProfileHeaderDto;
    }

    /**
     * This method would be use for getting Risk indicator data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return risk indicator Value data.
     */
    public String getRiskIndicatorByProjectId(Integer projectId) {
        String riskIndicatorValue = null;
        Query query = entityManager.createNativeQuery(Queries.RISK_INDICATOR_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> indicatorColorList = query.getResultList();
        if (!isEmpty(indicatorColorList)) {
            Object columns = indicatorColorList.get(0);
            if (!isEmpty(columns)) {
                riskIndicatorValue = columns.toString();
            }
        }
        if (("red").equals(riskIndicatorValue)) {
            riskIndicatorValue = DeliveryRiskIndicator.HIGH.getMessage();
        } else if (("orange").equals(riskIndicatorValue)) {
            riskIndicatorValue = DeliveryRiskIndicator.MODERATE.getMessage();
        } else if (("green").equals(riskIndicatorValue)) {
            riskIndicatorValue = DeliveryRiskIndicator.LOW.getMessage();
        }
        return riskIndicatorValue;
    }

    /**
     * This method would be use for getting Security Profile status related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return message.
     */
    public String getSecurityProfileStatusByProjectId(Integer projectId) {
        Query query = entityManager.createNativeQuery(Queries.SECURITY_PROFILE_STATUS_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> latestModifiedAtList = query.getResultList();
        if (isEmpty(latestModifiedAtList)) {
            return SecurityProfileStatus.NOT_COMPLETED.getMessage();
        }

        Object temp = latestModifiedAtList.get(0);
        Date lastModifiedDate = (Date) temp;
        int differenceOfDays = getDifferenceInNumberOfDays(lastModifiedDate);
        if (differenceOfDays > 90) {
            return SecurityProfileStatus.PASS_DUE.getMessage();
        } else {
            return SecurityProfileStatus.UPDATED.getMessage();
        }
    }

    /**
     * This method would be used for getting the difference in Number of days.
     * @param lastModifiedAt is the last Modified data.
     * @return this method would be return difference in Number of days.
     */
    public Integer getDifferenceInNumberOfDays(Date lastModifiedAt) {
        Date currentDate = new Date();
        final long DAY_IN_MILLIS = 86400000; //1000 * 60 * 60 * 24
        int diffInDays = (int) ((currentDate.getTime() - lastModifiedAt.getTime()) / DAY_IN_MILLIS);
        return diffInDays;
    }

    /**
     * This service would be use for saving and updating Security Profile related data.
     * @param securityProfileFormDataDto is the Profile data which needs to update.
     * @throws ActionFailureException exception would be throws if Security Profile data not saved.
     */
    @Transactional
    public void saveSecurityProfile(SecurityProfileFormDataDto securityProfileFormDataDto)
            throws ActionFailureException {
        List<ProjectSecurityProfile> projectSecurityProfileList = getProjectSecurityProfileByProjectId(securityProfileFormDataDto.getProjectId());
        List<SecuritySummaryDto> securitySummaryDtoList;
        if (CollectionUtils.isEmpty(projectSecurityProfileList)) {
            securitySummaryDtoList = onSaveGetUpdatedQuestionsList(securityProfileFormDataDto);
            addProjectSecurityProfile(securitySummaryDtoList, securityProfileFormDataDto);
        } else {
            securitySummaryDtoList = getUpdatedQuestionsList(securityProfileFormDataDto, projectSecurityProfileList);
            updateProjectSecurityProfile(securitySummaryDtoList, securityProfileFormDataDto, projectSecurityProfileList);
        }
        if (!CollectionUtils.isEmpty(securitySummaryDtoList)) {
            sendNotification(securityProfileFormDataDto, securitySummaryDtoList);
        }
    }

    /**
     * This method would be use for sending the Notification regarding Security Profile data.
     * @param securityProfileFormDataDto is the Profile data which needs to update.
     * @param securitySummaryDtoList is the list of security Summary data.
     * @throws ActionFailureException exception would be throws if Security Profile data not send.
     */
    public void sendNotification(SecurityProfileFormDataDto securityProfileFormDataDto, List<SecuritySummaryDto> securitySummaryDtoList) throws ActionFailureException {
        ProjectClientNameDto projectClientNameDto = riskSurveyService.getProjectAndClientNameByProjectId(securityProfileFormDataDto.getProjectId());
        List<Object[]> arrayList = entityManager.createNativeQuery(Queries.MODIFIED_BY_LIST_OF_CSL_RISK_SURVEY)
                .setParameter(1, securityProfileFormDataDto.getProjectId())
                .getResultList();
        ModifiedByListOfCSLDto modifiedByListOfCSLDto = ModifiedByListOfCSLDto.mapModifiedByListOfCSLDto(arrayList.get(0));
        modifiedByListOfCSLDto.setModifiedBy(resourceRepository.findFullNameFromUserId(CurrentUsernameUtil.getCurrentUsername()));
        emailService.setSecurityProfileValues(securitySummaryDtoList, projectClientNameDto, modifiedByListOfCSLDto, Constants.SECURITY_PROFILE);
    }

    /**
     * This method would be use for adding the Project Security Profile data.
     * @param securitySummaryDtoList is the list of security Summary data.
     * @param securityProfileFormDataDto is the Profile data which needs to add.
     * @throws ActionFailureException exception would be throws if Security Profile data not saved.
     */
    public void addProjectSecurityProfile(List<SecuritySummaryDto> securitySummaryDtoList, SecurityProfileFormDataDto securityProfileFormDataDto) throws ActionFailureException {
        updateSecurityProfileAccessHistory(securitySummaryDtoList, securityProfileFormDataDto.getProjectId());
        for (SecurityProfileFormDataAnswerDto answerCommentsEntry : securityProfileFormDataDto.getSecurityAnswersList()) {
            ProjectSecurityProfile projectSecurityProfile = new ProjectSecurityProfile();
            Project project = new Project();
            project.setId(securityProfileFormDataDto.getProjectId());
            projectSecurityProfile.setProject(project);
            SecurityAnswer securityAnswer = new SecurityAnswer();
            securityAnswer.setId(answerCommentsEntry.getSecurityAnswerId());
            projectSecurityProfile.setSecurityAnswer(securityAnswer);
            projectSecurityProfile.setComments(answerCommentsEntry.getComments());
            projectSecurityProfile.setSelected(answerCommentsEntry.getSelected());
            try {
                projectSecurityProfileRepository.save(projectSecurityProfile);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
        }
    }

    /**
     * This method would be use for getting list of Answers.
     * @return this method would be return getting list of SecurityAnswers data.
     */
    public List<AllSecurityAnswersDto> getAllAnswersList() {
        Query query = entityManager.createNativeQuery(Queries.GET_ALL_ANSWER_QUESTIONS_SECURITY_PROFILE);
        List<Object[]> allAnswersList = query.getResultList();
        List<AllSecurityAnswersDto> allSecurityAnswersDtoList = new ArrayList<>();
        for (Object[] row : allAnswersList) {
            AllSecurityAnswersDto allSecurityAnswersDto = SecurityProfileMapper.mapToAllSecurityAnswersDto(row);
            allSecurityAnswersDtoList.add(allSecurityAnswersDto);
        }
        return allSecurityAnswersDtoList;
    }

    /**
     * This method would be use for updating Project Security Profile related data.
     * @param securitySummaryDtoList is the list of security Summary data.
     * @param securityProfileFormDataDto is the Profile data which needs to add.
     * @param projectSecurityProfileList is the list of Project security Profile data.
     * @throws ActionFailureException exception would be throws if Security Profile data not update.
     */
    public void updateProjectSecurityProfile(List<SecuritySummaryDto> securitySummaryDtoList, SecurityProfileFormDataDto securityProfileFormDataDto,
                                             List<ProjectSecurityProfile> projectSecurityProfileList) throws ActionFailureException {
        if (!CollectionUtils.isEmpty(securitySummaryDtoList)) {
            updateSecurityProfileAccessHistory(securitySummaryDtoList, securityProfileFormDataDto.getProjectId());
        }
        int index = 0;
        for (ProjectSecurityProfile projectSecurityProfile : projectSecurityProfileList) {
            SecurityProfileFormDataAnswerDto updatedObject = securityProfileFormDataDto.getSecurityAnswersList().get(index);
            projectSecurityProfile.setComments(updatedObject.getComments());
            projectSecurityProfile.setSelected(updatedObject.getSelected());
            try {
                projectSecurityProfileRepository.save(projectSecurityProfile);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
            index++;
        }
    }

    /**
     * This method would be used for getting and Updating the list of Questions data.
     * @param securityProfileFormDataDto is the Profile data which needs to add.
     * @param projectSecurityProfileList is the list of Project security Profile data.
     * @return this method would be return list of security Summary data.
     */
    public List<SecuritySummaryDto> getUpdatedQuestionsList(SecurityProfileFormDataDto securityProfileFormDataDto,
                                                            List<ProjectSecurityProfile> projectSecurityProfileList) {
        int index = 0;
        List<SecuritySummaryDto> securitySummaryDtoList = new ArrayList<>();
        StringBuilder answerUpdatedFrom = new StringBuilder();
        StringBuilder answerUpdatedTo = new StringBuilder();
        String questionToBeCompared = "";
        SecuritySummaryDto securitySummaryDto = null;
        Boolean isQuestionMultiselect;
        Boolean isAnswerFromDbSelected;
        Boolean isAnswerFromFrontendSelected;
        for (ProjectSecurityProfile projectSecurityProfile : projectSecurityProfileList) {
            SecurityProfileFormDataAnswerDto updatedObject = securityProfileFormDataDto.getSecurityAnswersList().get(index);
            isQuestionMultiselect = projectSecurityProfile.getSecurityAnswer().getSecurityQuestions().getMultiSelect();
            isAnswerFromDbSelected = projectSecurityProfile.getSelected();
            isAnswerFromFrontendSelected = updatedObject.getSelected();
            if (!(isAnswerFromDbSelected).equals(isAnswerFromFrontendSelected)
                    || (isAnswerFromDbSelected && isAnswerFromFrontendSelected && isQuestionMultiselect)) {
                String updatedQuestion = projectSecurityProfile.getSecurityAnswer().getSecurityQuestions().getQuestion();
                if (!(questionToBeCompared).equals(updatedQuestion)) {
                    SecuritySummaryDto test = !CollectionUtils.isEmpty(securitySummaryDtoList) ? securitySummaryDtoList.get(securitySummaryDtoList.size() - 1) : null;
//                    if(test!=null)
                    if (!ObjectUtils.isEmpty(test) && (test.getSecurityAnswerUpdatedFrom()).equals(test.getSecurityAnswerUpdatedTo())) {
                        securitySummaryDtoList.remove(test);
                    }
                    questionToBeCompared = updatedQuestion;
                    securitySummaryDto = new SecuritySummaryDto();
                    securitySummaryDtoList.add(securitySummaryDto);
                    securitySummaryDto.setUpdatedSecurityQuestion("Q"+projectSecurityProfile.getSecurityAnswer().getSecurityQuestions().getPosition()+": "+updatedQuestion);
                    answerUpdatedFrom = new StringBuilder("");
                    answerUpdatedTo = new StringBuilder("");
                }
                String answer = projectSecurityProfile.getSecurityAnswer().getAnswer();
                if (Boolean.TRUE.equals(projectSecurityProfile.getSelected())) {
                    answerUpdatedFrom.append(Constants.SEPARATOR);
                    answerUpdatedFrom.append(answer);
                }
                if (Boolean.TRUE.equals(updatedObject.getSelected())) {
                    answerUpdatedTo.append(Constants.SEPARATOR);
                    answerUpdatedTo.append(answer);
                }

                if (!ObjectUtils.isEmpty(answerUpdatedFrom) && !ObjectUtils.isEmpty(securitySummaryDto)) {
                    securitySummaryDto.setSecurityAnswerUpdatedFrom(answerUpdatedFrom.substring(1));
                }


                if(ObjectUtils.isEmpty(answerUpdatedFrom)){
                    securitySummaryDto.setSecurityAnswerUpdatedFrom(Constants.NOT_APPLICABLE);
                }

                if (!ObjectUtils.isEmpty(answerUpdatedTo)) {
                    securitySummaryDto.setSecurityAnswerUpdatedTo(answerUpdatedTo.substring(1));
                }
            }
            index++;
        }
        SecuritySummaryDto test = !CollectionUtils.isEmpty(securitySummaryDtoList) ? securitySummaryDtoList.get(securitySummaryDtoList.size() - 1) : null;
        if (!ObjectUtils.isEmpty(test) && (test.getSecurityAnswerUpdatedFrom()).equals(test.getSecurityAnswerUpdatedTo())) {
            securitySummaryDtoList.remove(test);
        }

        return securitySummaryDtoList;
    }

    /**
     * This method would be use for saving and updating Security Profile related list of data.
     * @param securityProfileFormDataDto is the Profile data which needs to add.
     * @return this method would be return list of security Summary data.
     */
    public List<SecuritySummaryDto> onSaveGetUpdatedQuestionsList(SecurityProfileFormDataDto securityProfileFormDataDto) {
        List<AllSecurityAnswersDto> allSecurityAnswersDtoList = getAllAnswersList();
        int index = 0;
        List<SecuritySummaryDto> securitySummaryDtoList = new ArrayList<>();
        StringBuilder answerUpdatedTo = new StringBuilder();
        String questionToBeCompared = "";
        SecuritySummaryDto securitySummaryDto = null;
        Boolean isAnswerFromDbSelected;
        Boolean isAnswerFromFrontendSelected;
        for (AllSecurityAnswersDto allSecurityAnswer : allSecurityAnswersDtoList) {
            SecurityProfileFormDataAnswerDto updatedObject = securityProfileFormDataDto.getSecurityAnswersList().get(index);
            isAnswerFromDbSelected = allSecurityAnswer.getSelected();
            isAnswerFromFrontendSelected = updatedObject.getSelected();
            if (!(isAnswerFromDbSelected).equals(isAnswerFromFrontendSelected)) {
                String updatedQuestion = allSecurityAnswer.getSecurityQuestion();
                if (!(questionToBeCompared).equals(updatedQuestion)) {
                    questionToBeCompared = updatedQuestion;
                    securitySummaryDto = new SecuritySummaryDto();
                    securitySummaryDtoList.add(securitySummaryDto);
                    securitySummaryDto.setUpdatedSecurityQuestion(updatedQuestion);
                    answerUpdatedTo = new StringBuilder("");
                }
                String answer = allSecurityAnswer.getSecurityAnswer();
                if (Boolean.TRUE.equals(updatedObject.getSelected())) {
                    answerUpdatedTo.append(Constants.SEPARATOR);
                    answerUpdatedTo.append(answer);
                }
                if (!ObjectUtils.isEmpty(answerUpdatedTo) && !ObjectUtils.isEmpty(securitySummaryDto)) {
                    securitySummaryDto.setSecurityAnswerUpdatedTo(answerUpdatedTo.substring(1));
                }
                if (!ObjectUtils.isEmpty(securitySummaryDto)) {
                    securitySummaryDto.setSecurityAnswerUpdatedFrom(Constants.NOT_APPLICABLE);
                }

            }
            index++;
        }
        return securitySummaryDtoList;
    }

    /**
     * This method would be use for updating the Project Security Profile Access History data.
     * @param securitySummaryDtoList is the list of security Summary data.
     * @param projectId is the id of existing Project.
     * @throws ActionFailureException exception would be throws if Project Security Profile data not found.
     */
    public void updateSecurityProfileAccessHistory(List<SecuritySummaryDto> securitySummaryDtoList, Integer projectId) throws ActionFailureException {
        Date date = new Date();
        for (SecuritySummaryDto securitySummaryDto : securitySummaryDtoList) {
            SecurityProfileAccessHistory securityProfileAccessHistory = new SecurityProfileAccessHistory();
            securityProfileAccessHistory.setProjectId(projectId);
            securityProfileAccessHistory.setSecurityQuestion(securitySummaryDto.getUpdatedSecurityQuestion());
            securityProfileAccessHistory.setSecurityAnswerUpdatedFrom(securitySummaryDto.getSecurityAnswerUpdatedFrom());
            securityProfileAccessHistory.setSecurityAnswerUpdatedTo(securitySummaryDto.getSecurityAnswerUpdatedTo());
            securityProfileAccessHistory.setModifiedAt(date);
            securityProfileAccessHistory.setModifiedBy(CurrentUsernameUtil.getCurrentUsername());
            try {
                securityProfileAccessHistoryRepository.save(securityProfileAccessHistory);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
        }
    }

    /**
     * This method would be use for getting the Project Security Profile data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return list of project Security Profile.
     */
    public List<ProjectSecurityProfile> getProjectSecurityProfileByProjectId(int projectId) {
        List<ProjectSecurityProfile> projectSecurityProfileList = projectSecurityProfileRepository.findAllByProjectId(projectId);
        return projectSecurityProfileList;
    }

    /**
     * This service would be use for updating the Security Profile Access History Pass due data.
     * @param projectId is the id of existing Project.
     * @return this method would be return security Profile AccessHistory data.
     * @throws ActionFailureException exception would be throws if Project Status Report in Pass due data not found.
     */
    @Transactional
    public SecurityProfileAccessHistory updateSecurityProfileAccessHistoryInCaseOfPassDue(int projectId) throws ActionFailureException {
        SecurityProfileAccessHistory securityProfileAccessHistory = new SecurityProfileAccessHistory();
        securityProfileAccessHistory.setProjectId(projectId);
        securityProfileAccessHistory.setSecurityQuestion(Constants.PASS_DUE_SECURITY_QUESTION);
        securityProfileAccessHistory.setSecurityAnswerUpdatedFrom(Constants.PASS_DUE_SECURITY_ANSWER_UPDATED_FROM);
        securityProfileAccessHistory.setSecurityAnswerUpdatedTo(Constants.PASS_DUE_SECURITY_ANSWER_UPDATED_TO);
        securityProfileAccessHistory.setModifiedAt(new Date());
        securityProfileAccessHistory.setModifiedBy(CurrentUsernameUtil.getCurrentUsername());
        try {
            securityProfileAccessHistory = securityProfileAccessHistoryRepository.save(securityProfileAccessHistory);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
        updateModifiedAt(projectId);
        return securityProfileAccessHistory;
    }

    /**
     * This method would be use for updating the Modified data using projectId.
     * @param projectId is the id of existing Project.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void updateModifiedAt(int projectId) throws ActionFailureException {
        List<ProjectSecurityProfile> projectSecurityProfileList = getProjectSecurityProfileByProjectId(projectId);
        if (!CollectionUtils.isEmpty(projectSecurityProfileList)) {
            for (ProjectSecurityProfile projectSecurityProfile : projectSecurityProfileList) {
                projectSecurityProfile.setModifiedTime(new Date());
                try {
                    projectSecurityProfileRepository.save(projectSecurityProfile);
                } catch (Exception ex) {
                    throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
                }
            }
        }
    }


    public void mappedsecuritydata(int projectId, List<Integer> securityIds, String current_user) {
        for (Integer securityid : securityIds) {

            String createdBy = projectSecurityProfileRepository.findCreatedBy(projectId);
            System.out.println("my created by" + createdBy);
            projectSecurityProfileRepository.insertSecurityAnswer(projectId, securityid, createdBy, current_user);


        }
    }
}
