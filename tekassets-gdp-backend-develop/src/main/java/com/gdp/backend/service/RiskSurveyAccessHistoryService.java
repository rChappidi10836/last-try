package com.gdp.backend.service;

import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.ReportLogDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.ProjectRiskSurvey;
import com.gdp.backend.model.RiskSurveyAccessHistory;
import com.gdp.backend.repository.RiskSurveyAccessHistoryRepository;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * This Service class used for RiskSurvey AccessHistory.
 * @author gdp
 *
 */
@Service
public class RiskSurveyAccessHistoryService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    RiskSurveyAccessHistoryRepository riskSurveyAccessHistoryRepository;

    /**
     * This Service would be use for updating the RiskSurveyAccessHistory details using projectId. 
     * @param projectId is the id of existing Project.
     * @param projectRiskSurvey is the request.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void updateRiskSurveyAccessHistoryDetails(Integer projectId, ProjectRiskSurvey projectRiskSurvey) throws ActionFailureException {
        RiskSurveyAccessHistory riskSurveyAccessHistory = new RiskSurveyAccessHistory();
        riskSurveyAccessHistory.setProjectId(projectId);
        riskSurveyAccessHistory.setModifiedBy(CurrentUsernameUtil.getCurrentUsername());
        riskSurveyAccessHistory.setModifiedAt(new Date());
        try {
            riskSurveyAccessHistoryRepository.save(riskSurveyAccessHistory);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This Service would be use for getting the risk survey report log related information using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return list of ReportLogDto data.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public List<ReportLogDto> getRiskSurveyReportLogByProjectId(int projectId) throws ActionFailureException {
        Query query = entityManager.createNativeQuery(Queries.RISK_SURVEY_REPORT_LOG_WITH_PROJECT_ID, "ReportLogMapping");
        query.setParameter(1, projectId);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }
}
