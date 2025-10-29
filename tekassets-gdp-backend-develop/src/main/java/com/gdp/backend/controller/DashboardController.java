package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Constants;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.OpcoDto;
import com.gdp.backend.dto.ProjectFilterResponseDto;
import com.gdp.backend.dto.ProjectStatusResponseDto;
import com.gdp.backend.dto.SearchFilterDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.CreateProjectManuallyService;
import com.gdp.backend.service.ProjectFilterService;
import com.gdp.backend.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * This Controller class used for retrieving and saving the information related to the Dashboard.
 * @author gdp
 *
 */
@RestController
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    private ProjectFilterService projectFilterService;

    @Autowired
    private ProjectStatusService projectStatusService;

    @Autowired
    private CreateProjectManuallyService createProjectManuallyService;

    /**
     * This API would be use for getting Project related information.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class contains Project related data.
     * @throws ActionFailureException exception would be throws if Project data not found.
     */
    @ViewAccess
    @GetMapping("/get-project-status-details")
    public ResponseEntity<Response<ProjectStatusResponseDto>> getProjectDetails(
            @RequestParam("page") int page, @RequestParam("size") int size , @RequestParam("opcoId") String opcoId
    ) throws ActionFailureException {
        Response<ProjectStatusResponseDto> response = new Response<>();
        Constants.ENGAGEMENT_OPCO_ID = opcoId;
        response.setData(Arrays.asList(projectStatusService.getProjectStatusDefaultValue(page, size , opcoId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for populating the list of project data when we try to filter the project data.
     * @param active this is the boolean check either active or not active the project.
     * @param clientId is the id of existing client.
     * @param deliveryOrganizationTypeId this is the delivery Organization typeId.
     * @param cslId is the cslId of Project.
     * @param gdmId is the gdmId of Project.
     * @param businessUnitId is the UnitId of existing Project.
     * @param projectId is the id of existing Project.
     * @return response this class contains Project related data.
     * @throws ActionFailureException exception would be throws if project data not found.
     */
    @ViewAccess
    @GetMapping("/get-all-project-filter-details")
    public ResponseEntity<Response<ProjectFilterResponseDto>> getFilterDropdownOptions(
            @RequestParam("active") int active,
            @RequestParam("clientId") String clientId,
            @RequestParam("deliveryOrganizationTypeId") String deliveryOrganizationTypeId,
            @RequestParam("cslId") String cslId,
            @RequestParam("gdmId") String gdmId,
            @RequestParam("gddId") String gddId,
            @RequestParam("programManagerId") String programManagerId,
            @RequestParam("businessUnitId") String businessUnitId,
            @RequestParam("projectId") int projectId,
            @RequestParam("psId") int psId,
            @RequestParam("opcoId") String opcoId,
            @RequestParam("lodId") String lodId,
            @RequestParam("dmId") String deliverymodelId
    )
            throws ActionFailureException {
        Response<ProjectFilterResponseDto> response = new Response<>();
        response.setData(Arrays.asList(projectFilterService.getAllFiltersValue(active, clientId, deliveryOrganizationTypeId, cslId, gdmId, gddId, programManagerId, businessUnitId, projectId, psId , opcoId,lodId, deliverymodelId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for populating the list of project data when we try to filter the project data based on condition or criteria.
     * @param searchFilterDto is the data we need to search.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class contains Project related data based on condition or criteria.
     * @throws ActionFailureException exception would be throws if filter data not found.
     */
    @ViewAccess
    @PostMapping("/search-project-with-filter")
    public ResponseEntity<Response<ProjectStatusResponseDto>> getProjectWithFilterCriteria
            (@RequestBody @Valid SearchFilterDto searchFilterDto, @RequestParam("page") int page, @RequestParam("size") int size , @RequestParam("opcoId") String opcoId )
            throws ActionFailureException {
        System.out.println(searchFilterDto);
        Response<ProjectStatusResponseDto> response = new Response<>();
        response.setData(Arrays.asList(projectStatusService.getProjectStatusOnSearch(searchFilterDto, page, size,opcoId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-opco-list")
    public ResponseEntity<Response<List<OpcoDto>>> getOpcoList() throws ActionFailureException {
        Response<OpcoDto> response = new Response<>();
        response.setData(createProjectManuallyService.getOpcoList());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
