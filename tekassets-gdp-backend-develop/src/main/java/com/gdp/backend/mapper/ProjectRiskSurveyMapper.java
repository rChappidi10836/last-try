package com.gdp.backend.mapper;

import com.gdp.backend.dto.ProjectClientNameDto;
import com.gdp.backend.dto.RiskSurveyDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.Project;
import com.gdp.backend.model.ProjectRiskSurvey;

public class ProjectRiskSurveyMapper {

    private ProjectRiskSurveyMapper() throws ActionFailureException {
        throw new ActionFailureException(EStatusCode.ERROR_ON_INSTANTIATION.name());
    }

    public static ProjectRiskSurvey mapDtoToRiskSurvey(ProjectRiskSurvey projectRiskSurvey, RiskSurveyDto riskSurveyDto, Boolean isUpdate) {
        if (Boolean.FALSE.equals(isUpdate)) {
            Project project = new Project();
            project.setId(riskSurveyDto.getProjectId());
            projectRiskSurvey.setProject(project);
        }
        projectRiskSurvey.setRiskIndicator(riskSurveyDto.getIndicatorColor());
        projectRiskSurvey.setIsWarrantyApplicable(riskSurveyDto.getIsWarrantyApplicable());
        projectRiskSurvey.setLiabilityLimits(riskSurveyDto.getLiabilityLimits());
        projectRiskSurvey.setContractType(riskSurveyDto.getContractType());
        projectRiskSurvey.setCustomerCreditScore(riskSurveyDto.getCustomerCreditScore());
        projectRiskSurvey.setSensitiveData(riskSurveyDto.getSensitiveData());
        projectRiskSurvey.setIsComplianceAssociated(riskSurveyDto.getIsComplianceAssociated());
        projectRiskSurvey.setHardwareAndEquipment(riskSurveyDto.getHardwareAndEquipment());
        projectRiskSurvey.setIsCovered(riskSurveyDto.getIsCovered());
        projectRiskSurvey.setLocation(riskSurveyDto.getLocation());
        projectRiskSurvey.setTypeOfService(riskSurveyDto.getTypeOfService());
        projectRiskSurvey.setContractualDuration(riskSurveyDto.getContractualDuration());
        projectRiskSurvey.setIsEstablishedAccount(riskSurveyDto.getIsEstablishedAccount());
        projectRiskSurvey.setTotalProjectedRevenue(riskSurveyDto.getTotalProjectedRevenue());
        projectRiskSurvey.setEngagementWithTek(riskSurveyDto.getEngagementWithTek());
        projectRiskSurvey.setIs3rdPartyDependent(riskSurveyDto.getIs3rdPartyDependent());
        projectRiskSurvey.setHasUniqueChallenges(riskSurveyDto.getHasUniqueChallenges());
        projectRiskSurvey.setHasHighRiskCategory(riskSurveyDto.getHasHighRiskCategory());
        if (("Yes").equals(riskSurveyDto.getHasHighRiskCategory())) {
            projectRiskSurvey.setComments(riskSurveyDto.getComments());
        }else if (("No").equals(riskSurveyDto.getHasHighRiskCategory())) {
            projectRiskSurvey.setComments(riskSurveyDto.getComments());
        } else {
            projectRiskSurvey.setComments(null);
        }
        return projectRiskSurvey;
    }

    public static RiskSurveyDto mapRiskSurveyToDto(ProjectRiskSurvey projectRiskSurvey, Integer projectId) {
        RiskSurveyDto riskSurveyDto = new RiskSurveyDto();
        riskSurveyDto.setProjectId(projectId);
        riskSurveyDto.setIndicatorColor(projectRiskSurvey.getRiskIndicator());
        riskSurveyDto.setIsWarrantyApplicable(projectRiskSurvey.getIsWarrantyApplicable());
        riskSurveyDto.setLiabilityLimits(projectRiskSurvey.getLiabilityLimits());
        riskSurveyDto.setContractType(projectRiskSurvey.getContractType());
        riskSurveyDto.setCustomerCreditScore(projectRiskSurvey.getCustomerCreditScore());
        riskSurveyDto.setSensitiveData(projectRiskSurvey.getSensitiveData());
        riskSurveyDto.setIsComplianceAssociated(projectRiskSurvey.getIsComplianceAssociated());
        riskSurveyDto.setHardwareAndEquipment(projectRiskSurvey.getHardwareAndEquipment());
        riskSurveyDto.setIsCovered(projectRiskSurvey.getIsCovered());
        riskSurveyDto.setLocation(projectRiskSurvey.getLocation());
        riskSurveyDto.setTypeOfService(projectRiskSurvey.getTypeOfService());
        riskSurveyDto.setContractualDuration(projectRiskSurvey.getContractualDuration());
        riskSurveyDto.setIsEstablishedAccount(projectRiskSurvey.getIsEstablishedAccount());
        riskSurveyDto.setTotalProjectedRevenue(projectRiskSurvey.getTotalProjectedRevenue());
        riskSurveyDto.setEngagementWithTek(projectRiskSurvey.getEngagementWithTek());
        riskSurveyDto.setIs3rdPartyDependent(projectRiskSurvey.getIs3rdPartyDependent());
        riskSurveyDto.setHasUniqueChallenges(projectRiskSurvey.getHasUniqueChallenges());
        riskSurveyDto.setHasHighRiskCategory(projectRiskSurvey.getHasHighRiskCategory());
        if (("Yes").equals(projectRiskSurvey.getHasHighRiskCategory())) {
            riskSurveyDto.setComments(projectRiskSurvey.getComments());
        } else {
            riskSurveyDto.setComments(null);
        }
        return riskSurveyDto;
    }

    public static RiskSurveyDto mapProjectClientNameToRiskSurveyDto(ProjectClientNameDto projectClientNameDto) {
        RiskSurveyDto riskSurveyDto = new RiskSurveyDto();
        riskSurveyDto.setProjectName(projectClientNameDto.getProjectName());
        riskSurveyDto.setAccountName(projectClientNameDto.getAccountName());
        return riskSurveyDto;
    }
}
