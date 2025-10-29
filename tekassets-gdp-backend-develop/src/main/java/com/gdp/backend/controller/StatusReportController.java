package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Utils;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.ProjectStatus;
import com.gdp.backend.model.SecurityProfileAccessHistory;
import com.gdp.backend.repository.ProjectsRepository;
import com.gdp.backend.service.GDPUserDetailsService;
import com.gdp.backend.service.SecurityProfileService;
import com.gdp.backend.service.StatusReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * This Controller class used for retrieving, saving, update and deleting the information related to the Status Report.
 * @author gdp
 *
 */
@RestController
@RequestMapping("status-report")
public class StatusReportController {

    @Autowired
    StatusReportService statusReportService;

    @Autowired
    SecurityProfileService securityProfileService;
    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    private Utils utils;

    @Autowired
    GDPUserDetailsService gdpUserDetailsService;


    /**
     * This API would be use for getting Project status report related information using projectId.
     * @param projectId is the id of existing Project.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class contains Project status report related data.
     * @throws ActionFailureException exception would be throws if Project status data not found.
     */
    @ViewAccess
    @GetMapping("/get-status-report-by-projectId/{projectId}")
    public ResponseEntity<Response<StatusReportDto>> getStatusReport(@PathVariable int projectId,
                                                                     @RequestParam("page") int page, @RequestParam("size") int size) throws ActionFailureException {
        Response<StatusReportDto> response = new Response<>();
        if(utils.areOpcoIdsEqual(projectId)){
        response.setData(Arrays.asList(statusReportService.getStatusReportByProjectId(page, size, projectId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.setStatusCode(EStatusCode.ERROR_ON_ID.name());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * This API would be use for saving the Project status report related data.
     * @param statusReportCreateNewDto is the data which needs to save.
     * @return response this class saving the Project status report related data.
     * @throws ActionFailureException exception would be throws if Project status data not saved.
     */
    @EditAccess
    @PostMapping("/save-status-report")
    public ResponseEntity<Response<ProjectStatus>> saveStatusReport(@RequestBody StatusReportCreateNewDto statusReportCreateNewDto)
            throws ActionFailureException {
        Response<ProjectStatus> response = new Response<>();
        response.setData(Arrays.asList(statusReportService.createNewStatusReport(statusReportCreateNewDto)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for updating the Project status report related data.
     * @param statusReportCreateNewDto is the data which needs to update.
     * @return response this class updating the Project status report related data.
     * @throws ActionFailureException exception would be throws if Project status data not found.
     */
    @EditAccess
    @PostMapping("/update-status-report")
    public ResponseEntity<Response<ProjectStatus>> updateStatusReport(@RequestBody StatusReportCreateNewDto statusReportCreateNewDto)
            throws ActionFailureException {
        Response<ProjectStatus> response = new Response<>();
        response.setData(Arrays.asList(statusReportService.updateStatusReport(statusReportCreateNewDto)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for deleting the Project status report related data.
     * @param statusReportCreateNewDto is the data which needs to delete.
     * @return response this class deleting the Project status report related data.
     * @throws ActionFailureException exception would be throws if Project status data not found.
     */
    @EditAccess
    @DeleteMapping("/delete-status-report")
    public ResponseEntity<Response<ProjectStatus>> deleteStatusReport(@RequestBody StatusReportCreateNewDto statusReportCreateNewDto)
            throws ActionFailureException {
        Response<ProjectStatus> response = new Response<>();
        response.setData(Arrays.asList(statusReportService.deleteStatusReport(statusReportCreateNewDto)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for getting the Project Delivery Hours related data using projectId.
     * @param projectId is the id of existing Project.
     * @return response this class getting the Project Delivery Hours related data.
     * @throws ActionFailureException exception would be throws if Project Delivery Hours data not found.
     */
    @ViewAccess
    @GetMapping("/get-delivery-hours-by-projectId/{projectId}")
    public ResponseEntity<Response<DeliveryHoursResponseDTO>> getDeliveryHours(@PathVariable int projectId) throws ActionFailureException {
        Response<DeliveryHoursResponseDTO> response = new Response<>();
//        response.setData(statusReportService.getDeliveryHoursByProjectId(projectId));
//        response.setStatusCode(EStatusCode.SUCCESS.name());
//        return new ResponseEntity<>(response, HttpStatus.OK);
        String Resource_OpcoId = gdpUserDetailsService.getUserOpcoId();
        String Project_OpcoId = projectsRepository.findOpcoId(projectId);
        System.out.println("Client opco id is : "+Project_OpcoId);
        if(Resource_OpcoId.equals(Project_OpcoId)){
            response.setData(statusReportService.getDeliveryHoursByProjectId(projectId));
            response.setStatusCode(EStatusCode.SUCCESS.name());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return null;
        }

    }

    /**
     * This API would be use for getting the Project Delivery Hours related data using projectId with week ending Date.
     * @param projectId is the id of existing Project.
     * @param weekEndingDate is the Project Delivery Hours week ending Date.
     * @return response this class getting the Project Delivery Hours week ending Date related data.
     * @throws ActionFailureException exception would be throws if Project Delivery Hours WED data not found.
     */
    @ViewAccess
    @GetMapping("/get-delivery-hours-by-projectId-wed/{projectId}")
    public ResponseEntity<Response<DeliveryHoursDto>> getDeliveryHoursByWED(@PathVariable int projectId, @RequestParam("weekEndingDate") String weekEndingDate) throws ActionFailureException {
        Response<DeliveryHoursDto> response = new Response<>();
//        response.setData(statusReportService.getDeliveryHoursByProjectIdWED(projectId, weekEndingDate));
//        response.setStatusCode(EStatusCode.SUCCESS.name());
//        return new ResponseEntity<>(response, HttpStatus.OK);
        String Resource_OpcoId = gdpUserDetailsService.getUserOpcoId();
        String Project_OpcoId = projectsRepository.findOpcoId(projectId);
        System.out.println("Client opco id is : "+Project_OpcoId);
        if(Resource_OpcoId.equals(Project_OpcoId)){
            response.setData(statusReportService.getDeliveryHoursByProjectIdWED(projectId, weekEndingDate));
            response.setStatusCode(EStatusCode.SUCCESS.name());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return null;
        }


    }

    /**
     * This API would be use for updating the Project Status Report in Pass due data.
     * @param projectId is the id of existing Project.
     * @return response this class updating the Project Status Report in Pass due data.
     * @throws ActionFailureException exception would be throws if Project Status Report in Pass due data not found.
     */
    @EditAccess
    @PostMapping("/update-status-report-in-pass-due")
    public ResponseEntity<Response<SecurityProfileAccessHistory>> updateStatusReportInPassDue(@RequestBody int projectId)
            throws ActionFailureException {
        Response<SecurityProfileAccessHistory> response = new Response<>();
        response.setData(Arrays.asList(securityProfileService.updateSecurityProfileAccessHistoryInCaseOfPassDue(projectId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for saving the Project Delivery Hours related data.
     * @param deliveryHours is the data which needs to save.
     * @return response this class saving the Project Delivery Hours related data.
     * @throws ActionFailureException exception would be throws if Project Delivery Hours related data not save.
     */
    @EditAccess
    @PostMapping("/save-delivery-hours")
    public ResponseEntity<Response> saveDeliveryHours(@RequestBody DeliveryHoursRequestDTO deliveryHours)
            throws ActionFailureException {
        Response response = new Response();
        statusReportService.saveDeliveryHours(deliveryHours);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
