package com.gdp.backend.controller;

import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.OpportunityResponseDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * This Controller class used for retrieving the Opportunity related information.
 * @author gdp
 *
 */
@RestController
@RequestMapping("opportunity")
public class OpportunityController {

    @Autowired
    OpportunityService opportunityService;

    /**
     * This API would be use for getting list of opportunity related information.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class contains opportunity related data.
     * @throws ActionFailureException exception would be throws if opportunity data not found.
     */
    @ViewAccess
    @GetMapping("/get-all-opportunity-details")
    public ResponseEntity<Response<OpportunityResponseDto>> getOpportunityDetails(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("opcoId") String opcoId) throws ActionFailureException {
        Response<OpportunityResponseDto> response = new Response<>();
        response.setData(Arrays.asList(opportunityService.getAllOpportunityValues(page, size, opcoId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
