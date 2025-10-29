package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Constants;
import com.gdp.backend.dto.ProjectClientNameDto;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.RiskSurveyExportToExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;

/**
 * This Controller class used for exporting the Risk Survey data into Excel files.
 * @author gdp
 *
 */
@RestController
@RequestMapping("risk-survey-excel")
public class RiskSurveyExportToExcelController {

    @Autowired
    RiskSurveyExportToExcelService riskSurveyExportToExcelService;

    /**
     * This API would be use for getting list of Project related data for sample risk survey into the excel file.
     * @param projectId is the id of existing Project.
     * @param projectClientNameDto is the project client nameDto data.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @return response this class contains list of project data.
     * @throws ActionFailureException exception would be throws if Project data not found.
     */
    @ViewAccess
    @PostMapping("/get-sample-risk-survey/{projectId}")
    public ResponseEntity<InputStreamResource> getAllProjectDetails(@PathVariable int projectId, @RequestBody @Valid ProjectClientNameDto projectClientNameDto
            , HttpServletResponse httpServletResponse) throws ActionFailureException {
        ByteArrayInputStream inputStream = riskSurveyExportToExcelService.exportToExcelExistingFile(projectId,
                projectClientNameDto, httpServletResponse, Constants.RISK_SURVEY);
        HttpHeaders headers = new HttpHeaders();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Risk Survey.xlsx";
        headers.add(headerKey, headerValue);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }
}
