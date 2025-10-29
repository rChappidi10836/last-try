package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.CreateProjectFormDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.CreateAndEditProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * This Controller class used for create and edit Project Details and Data.
 * @author gdp
 *
 */
@RestController
@RequestMapping("edit-project")
public class EditProjectController {

    @Autowired
    CreateAndEditProjectService createAndEditProjectService;

    /**
     * This API would be use for edit the existing Project details.
     * @param createProjectFormDto is the data which we need to update.
     * @return this class contains Project related data.
     * @throws ActionFailureException exception would be throws if Project not found.
     */
    @EditAccess
    @PostMapping("/edit-project-details")
    public ResponseEntity<Response<CreateProjectFormDto>> editProjectDetails(@RequestBody @Valid CreateProjectFormDto createProjectFormDto)
            throws ActionFailureException {
        Response<CreateProjectFormDto> response = new Response<>();
        response.setData(Arrays.asList(createAndEditProjectService.updateProjectDetails(createProjectFormDto)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
