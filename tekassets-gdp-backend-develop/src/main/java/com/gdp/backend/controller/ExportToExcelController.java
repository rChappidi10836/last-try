package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.dto.SearchFilterDto;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.ExportToExcelService;
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
 * This Controller class used for export project data into the excel files either all Project details or based on condition data will be populate in excel file.
 * @author gdp
 *
 */
@RestController
@RequestMapping("excel")
public class ExportToExcelController {

    @Autowired
    ExportToExcelService exportToExcelService;

    /**
     * This API would be use for getting the list of project details and populate in excel file.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response. 
     * @return response this class contains Project details.
     * @throws ActionFailureException exception would be throws if Project data not found.
     */
    @ViewAccess
    @GetMapping("/get-all-project-details")
    public ResponseEntity<InputStreamResource> getAllProjectDetails(HttpServletResponse httpServletResponse) throws ActionFailureException {
        ByteArrayInputStream inputStream = exportToExcelService.getAllProjectDetails(httpServletResponse);
        HttpHeaders headers = new HttpHeaders();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All Engagement Details.xlsx";
        headers.add(headerKey, headerValue);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    /**
     * This API would be use for getting the list of project details and populate in excel file based on search data.
     * @param searchFilterDto is the search filter data.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @return this class contains Project details.
     * @throws ActionFailureException exception would be throws if search data not found.
     */
    @ViewAccess
    @PostMapping("/project-details-on-filter")
    public ResponseEntity<InputStreamResource> getProjectDetailsOnSearch(@RequestBody @Valid SearchFilterDto searchFilterDto, HttpServletResponse httpServletResponse ,@RequestParam("opcoId") String opcoId) throws ActionFailureException {
        ByteArrayInputStream inputStream = exportToExcelService.getProjectDetailsOnSearch(httpServletResponse, searchFilterDto , opcoId);
        HttpHeaders headers = new HttpHeaders();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Filtered Engagement Details.xlsx";
        headers.add(headerKey, headerValue);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    /**
     * This API would be use for getting the list of Opportunity details and populate into the excel file.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @return this class contains Opportunity details.
     * @throws ActionFailureException exception would be throws if Opportunity data not found.
     */
    @ViewAccess
    @GetMapping("/get-all-opportunity-details")
    public ResponseEntity<InputStreamResource> getAllOpportunityDetails(HttpServletResponse httpServletResponse,@RequestParam("OpcoId") String OpcoId) throws ActionFailureException {
        ByteArrayInputStream inputStream = exportToExcelService.getAllOpportunityDetails(httpServletResponse,OpcoId);
        HttpHeaders headers = new HttpHeaders();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All Opportunity Details.xlsx";
        headers.add(headerKey, headerValue);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }
}
