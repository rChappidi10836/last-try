package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.AttachToExistingProjectDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.AttachToExistingProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This Controller class used for Attaching to existing Project and also used for retrieving the complete data info about the Projects and opportunity.
 * @author gdp
 *
 */
@RestController
@RequestMapping("attach-opportunity")
public class AttachToExistingProjectController {

    @Autowired
    AttachToExistingProjectService attachToExistingProjectService;

    /**
     * This API would be use for the getting the list of Project details.
     * @return response with HttpStatus.
     * @throws ActionFailureException exception would be throws, if Project Data is not found.
     */
    @ViewAccess
    @GetMapping("/get-all-project-details")
    public ResponseEntity<Response<AttachToExistingProjectDto>> getAllProjectDetails() throws ActionFailureException {
        Response<AttachToExistingProjectDto> response = new Response<>();
        response.setData(attachToExistingProjectService.getAllProjectAndClient());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the attaching Opportunity to Project and Mapping the data.
     * @param attachToExistingProjectDto AttachToExistingProjectDto.
     * @return response with HttpStatus.
     * @throws ActionFailureException exception would be throws, if Opportunity Data is not found.
     */
    @EditAccess
    @PostMapping("/add-opportunity-to-project")
    public ResponseEntity<Response> attachOpportunityToProject(@RequestBody @Valid AttachToExistingProjectDto
                                                                       attachToExistingProjectDto)
            throws ActionFailureException {
        Response response = new Response<>();
        attachToExistingProjectService.addOpportunityToProject(attachToExistingProjectDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
