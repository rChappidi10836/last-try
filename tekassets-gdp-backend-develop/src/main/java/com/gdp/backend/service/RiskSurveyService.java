package com.gdp.backend.service;

import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.ProjectClientNameDto;
import com.gdp.backend.dto.RiskSurveyDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.mapper.ProjectRiskSurveyMapper;
import com.gdp.backend.model.ProjectRiskSurvey;
import com.gdp.backend.repository.ProjectRiskSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This Service class used for RiskSurvey details.
 * @author gdp
 *
 */
@Service
public class RiskSurveyService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectRiskSurveyRepository projectRiskSurveyRepository;

    @Autowired
    RiskSurveyAccessHistoryService riskSurveyAccessHistoryService;

    /**
     * This Service would be use for saving and updating the risk survey related information.
     * @param riskSurveyDto is the request pojo class.
     * @return this method would be return RiskSurveyDto.
     * @throws ActionFailureException exception would be throws if RiskSurvey data not found.
     */
    @Transactional
    public RiskSurveyDto saveOrUpdateRiskSurvey(RiskSurveyDto riskSurveyDto) throws ActionFailureException {
        ProjectRiskSurvey projectRiskSurvey = getRiskSurveyByProjectId(riskSurveyDto.getProjectId());
        if (isEmpty(projectRiskSurvey)) {
            projectRiskSurvey = saveRiskSurvey(addRiskSurvey(riskSurveyDto));
        } else {
            projectRiskSurvey = saveRiskSurvey(updateRiskSurvey(projectRiskSurvey, riskSurveyDto));
        }
        riskSurveyAccessHistoryService.updateRiskSurveyAccessHistoryDetails(riskSurveyDto.getProjectId(), projectRiskSurvey);
        return ProjectRiskSurveyMapper.mapRiskSurveyToDto(projectRiskSurvey, riskSurveyDto.getProjectId());
    }

    /**
     * This method would be use for saving and updating the risk survey data.
     * @param projectRiskSurvey is the request for RiskSurvey.
     * @return this method would be return ProjectRiskSurvey.
     * @throws ActionFailureException exception would be throws if risk survey not found.
     */
    public ProjectRiskSurvey saveRiskSurvey(ProjectRiskSurvey projectRiskSurvey) throws ActionFailureException {
        try {
            return projectRiskSurveyRepository.save(projectRiskSurvey);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This Service would be use for saving and updating the risk survey data.
     * @param riskSurveyDto is the request RiskSurvey.
     * @return this method would be return ProjectRiskSurvey.
     */
    public ProjectRiskSurvey addRiskSurvey(RiskSurveyDto riskSurveyDto) {
        ProjectRiskSurvey projectRiskSurvey = new ProjectRiskSurvey();
        projectRiskSurvey = ProjectRiskSurveyMapper.mapDtoToRiskSurvey(projectRiskSurvey, riskSurveyDto, false);
        return projectRiskSurvey;
    }

    /**
     * This method would be use for updating the risk survey related data.
     * @param projectRiskSurvey is the request parameter.
     * @param riskSurveyDto is the request parameter. 
     * @return this method would be return Project RiskSurvey details.
     */
    public ProjectRiskSurvey updateRiskSurvey(ProjectRiskSurvey projectRiskSurvey, RiskSurveyDto riskSurveyDto) {
        return ProjectRiskSurveyMapper.mapDtoToRiskSurvey(projectRiskSurvey, riskSurveyDto, true);
    }

    /**
     * This service would be use for getting the risk survey related data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return RiskSurveyDto.
     */
    public RiskSurveyDto getRiskSurvey(Integer projectId) {
        RiskSurveyDto riskSurveyDto;
        Query query = entityManager.createNativeQuery(Queries.RISK_SURVEY_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> riskSurveyList = query.getResultList();
        if (CollectionUtils.isEmpty(riskSurveyList)) {
            return ProjectRiskSurveyMapper.mapProjectClientNameToRiskSurveyDto(getProjectAndClientNameByProjectId(projectId));
        }
        riskSurveyDto = new RiskSurveyDto(riskSurveyList.get(0));
        return riskSurveyDto;
    }

    /**
     * This method would be use for getting Project and Client Name using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return projectClientNameDto.
     */
    public ProjectClientNameDto getProjectAndClientNameByProjectId(int projectId) {
        Query query = entityManager.createNativeQuery(Queries.PROJECT_AND_CLIENT_NAME_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> riskSurveyList = query.getResultList();
        ProjectClientNameDto projectClientNameDto = ProjectClientNameDto.mapToProjectClientNameDto(riskSurveyList.get(0));
        return projectClientNameDto;
    }

    /**
     * This method would be use for getting RiskSurvey details using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return ProjectRiskSurvey data.
     */
    public ProjectRiskSurvey getRiskSurveyByProjectId(int projectId) {
        Optional<ProjectRiskSurvey> optionalProjectRiskSurvey = projectRiskSurveyRepository.findByProjectId(projectId);
        if (!optionalProjectRiskSurvey.isPresent()) {
            return null;
        }
        return optionalProjectRiskSurvey.get();
    }
}
