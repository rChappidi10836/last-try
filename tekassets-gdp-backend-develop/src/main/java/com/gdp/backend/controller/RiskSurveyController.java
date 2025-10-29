package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Utils;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.ReportLogDto;
import com.gdp.backend.dto.RiskSurveyDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.RiskSurveyAccessHistoryService;
import com.gdp.backend.service.RiskSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * This Controller class used for retrieving and saving the information related to the Risk Survey.
 * @author gdp
 *
 */
@RestController
@RequestMapping("risk-survey")
public class RiskSurveyController {

    @Autowired
    RiskSurveyService riskSurveyService;

    @Autowired
    RiskSurveyAccessHistoryService riskSurveyAccessHistoryService;

    @Autowired
    private Utils utils;
    /**
     * This API would be use for saving the risk survey related information.
     * @param riskSurveyDto is the column name for risk survey.
     * @return response this class contains risk survey related data.
     * @throws ActionFailureException exception would be throws if risk survey data not saved.
     */
    @EditAccess
    @PostMapping("/save-risk-survey")
    public ResponseEntity<Response<RiskSurveyDto>> saveRiskSurveyForm(@RequestBody @Valid RiskSurveyDto riskSurveyDto)
            throws ActionFailureException {
        Response<RiskSurveyDto> response = new Response<>();
        response.setData(Arrays.asList(riskSurveyService.saveOrUpdateRiskSurvey(riskSurveyDto)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for getting the risk survey related information.
     * @param projectId is the id of existing Project.
     * @return response this class contains risk survey related data.
     */
    @ViewAccess
    @GetMapping("/get-risk-survey/{projectId}")
    public ResponseEntity<Response<RiskSurveyDto>> getRiskSurvey(@PathVariable int projectId) {
        Response<RiskSurveyDto> response = new Response<>();
        if(utils.areOpcoIdsEqual(projectId)) {
            response.setData(Arrays.asList(riskSurveyService.getRiskSurvey(projectId)));
            response.setStatusCode(EStatusCode.SUCCESS.name());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setStatusCode(EStatusCode.ERROR_ON_ID.name());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * This API would be use for getting the risk survey report log related information.
     * @param projectId is the id of existing Project.
     * @return response this class contains risk survey report log related data.
     * @throws ActionFailureException exception would be throws if report log data not found.
     */
    @ViewAccess
    @GetMapping("/get-risk-survey-report-log/{projectId}")
    public ResponseEntity<Response<ReportLogDto>> getRiskSurveyReportLog(@PathVariable int projectId)
            throws ActionFailureException {
        Response<ReportLogDto> response = new Response<>();
        response.setData(riskSurveyAccessHistoryService.getRiskSurveyReportLogByProjectId(projectId));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
