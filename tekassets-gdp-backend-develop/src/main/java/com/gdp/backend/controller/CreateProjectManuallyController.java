package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Utils;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.Opco;
import com.gdp.backend.repository.OpcoRepository;
import com.gdp.backend.repository.ProjectsRepository;
import com.gdp.backend.service.CreateProjectManuallyService;
import com.gdp.backend.service.GDPUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Optional;


/**
 * This Controller class used for creating the Project manually.
 * @author gdp
 *
 */
@RestController
@RequestMapping("create-project")
public class CreateProjectManuallyController {

    @Autowired
    CreateProjectManuallyService createProjectManuallyService;

    EntityManager entityManager;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    OpcoRepository opcoRepository;

    @Autowired
    private Utils utils;

    @Autowired
    GDPUserDetailsService gdpUserDetailsService;

    /**
     * This API would be use for the getting the Sales Organization related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Sales Organization Data is not found.
     */
    @ViewAccess
    @GetMapping("/get-sales-organization-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getSalesOrganizationDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getSalesOrganizationDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Staffing Sales Region related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Sales Organization Data is not found.
     */
    @ViewAccess
    @GetMapping("/get-staffing-sales-region-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getStaffingSalesRegionDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getStaffingSalesRegionDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Business Unit related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Business Unit Data is not found.
     */
    @ViewAccess
    @GetMapping("/get-business-unit-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getBusinessUnitDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getBusinessUnitDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Practice Engagement related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Practice Engagement Data is not found.
     */
    @ViewAccess
    @GetMapping("/get-practice-engagement-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getPracticeEngagementDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getPracticeEngagementDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Delivery Model related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Practice Delivery Model is not found.
     */
    @ViewAccess
    @GetMapping("/get-delivery-model-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getDeliveryModelDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getDeliveryModelDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Location Of Services related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Location Of Services data is not found.
     */
    @ViewAccess
    @GetMapping("/get-location-of-services-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getLocationOfServicesDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getLocationOfServicesDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Contract Type related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Contract Type data is not found.
     */
    @ViewAccess
    @GetMapping("/get-contract-type-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getContractTypeDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getContractTypeDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Primary Service Type related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Primary Service Type data is not found.
     */
    @ViewAccess
    @GetMapping("/get-primary-service-type-dropdown")
    public ResponseEntity<Response<CreateProjectDropdownDto>> getPrimaryServiceTypeDropdown() throws ActionFailureException {
        Response<CreateProjectDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getPrimaryServiceTypeDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting the Primary Resource Role related data.
     * @return this class contains ResourceRoleDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Primary Resource Role data is not found.
     */
    @ViewAccess
    @GetMapping("/get-resource-role-dropdown")
    public ResponseEntity<Response<ResourceRoleDropdownDto>> getResourceRoleDropdown() throws ActionFailureException {
        Response<ResourceRoleDropdownDto> response = new Response<>();
        response.setData(createProjectManuallyService.getResourceRoleDropdownValues());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the Creating Project Manually.
     * @param createProjectFormDto this class use for the Creating Project Manually related data.
     * @param isSourceOpportunity this is check Opportunity.
     * @return this class contains CreateProjectFormDto Response.
     * @throws ActionFailureException exception would be throws, if Project data is not saved.
     */
    @EditAccess
    @PostMapping("/create-project-manually")
    public ResponseEntity<Response<CreateProjectFormDto>> createProjectManually(@RequestBody @Valid CreateProjectFormDto createProjectFormDto,
                                                                                @RequestParam("isSourceOpportunity") boolean isSourceOpportunity)
            throws ActionFailureException {
        Response<CreateProjectFormDto> response = new Response<>();
        response.setData(Arrays.asList(createProjectManuallyService.addProject(createProjectFormDto, isSourceOpportunity)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/check-if-project-name-exists")
    public ResponseEntity<Response<Boolean>> searchProjectByName( @RequestParam("projectName") String projectName  )
            throws ActionFailureException {
        Response<Boolean> response = new Response<>();
        response.setData(Arrays.asList(createProjectManuallyService.findProjectByName(projectName)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the Searching Project Resource and populating list of Resource data.
     * @param userSearchDto that needs to search.
     * @param page this is number of current page.
     * @param size this is number of items which would be populate.
     * @return this class contains UserSearchResponseDto Response.
     * @throws ActionFailureException exception would be throws, if Searching Project Resource data is not found.
     */
    @EditAccess
    @PostMapping("/user-search")
    public ResponseEntity<Response<UserSearchResponseDto>> getResourceList(@RequestBody @Valid UserSearchDto userSearchDto
            ,@RequestParam("OpcoID") String opcoID , @RequestParam("page") int page, @RequestParam("size") int size)
            throws ActionFailureException {
        Response<UserSearchResponseDto> response = new Response<>();
        //String Resource_OpcoId = gdpUserDetailsService.getUserOpcoId();
        response.setData(Arrays.asList(createProjectManuallyService.getResourceData(userSearchDto,opcoID, page, size)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for the getting Project Details data using ProjectId.
     * @param projectId use by class for getting the Project Details.
     * @return this class contains CreateProjectFormDto Response.
     * @throws ActionFailureException exception would be throws, if SProject Details data is not found.
     */
    @ViewAccess
    @GetMapping("/find-project-by-id/{projectId}")
    public ResponseEntity<Response<CreateProjectFormDto>> getProjectDetailsById(@PathVariable int projectId)
            throws ActionFailureException {
        String Resource_OpcoId = gdpUserDetailsService.getUserOpcoId();
        String Project_OpcoId = projectsRepository.findOpcoId(projectId);
        Optional<Opco> byId = opcoRepository.findById(Integer.valueOf(Project_OpcoId));
        System.out.println("Client opco id is : "+Project_OpcoId);
        if(utils.areOpcoIdsEqual(projectId)){
            Response<CreateProjectFormDto> response = new Response<>();
            response.setData(Arrays.asList(createProjectManuallyService.findProjectById(projectId)));
            response.setStatusCode(EStatusCode.SUCCESS.name());
            response.setOpcoid(Project_OpcoId+" "+byId.get().getOpcoName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
//            return null;
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found");
            Response<CreateProjectFormDto> response = new Response<>();
            response.setStatusCode(EStatusCode.ERROR_ON_ID.name());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        }
    }




    /**
     * This API would be use for the getting Report Log Details data using ProjectId.
     * @param projectId use by class for getting the Project Details.
     * @return this class contains ReportLogDto Response.
     * @throws ActionFailureException exception would be throws, if Report Log Details data is not found.
     */
    @ViewAccess
    @GetMapping("/get-report-log/{projectId}")
    public ResponseEntity<Response<ReportLogDto>> getReportLogById(@PathVariable int projectId)
            throws ActionFailureException {
        Response<ReportLogDto> response = new Response<>();
        response.setData(createProjectManuallyService.getReportLogByProjectId(projectId));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
