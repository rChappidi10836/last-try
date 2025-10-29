package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.DeliveryMilestoneTypeDto;
import com.gdp.backend.dto.MilestoneAddDto;
import com.gdp.backend.dto.MilestoneReadOnlyDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.MilestonesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * This Controller class used for retrieving and saving the information related to the Milestones data.
 * @author gdp
 *
 */
@RestController
@RequestMapping("milestones")
public class MilestonesController {

    @Autowired
    MilestonesService milestonesService;

    /**
     * This API would be use for getting Milestones related information.
     * @param projectId is the id of existing Project.
     * @param weekEndingDate is the week ending Date.
     * @param page this is the number of current page.
     * @param size this is the number of items which would be populate.
     * @return response this class contains Milestones related data.
     */
    @ViewAccess
    @GetMapping("/get-milestones-by-projectId/{projectId}")
    public ResponseEntity<Response<MilestoneReadOnlyDto>> getMilestones(@PathVariable int projectId,
                                                                        @RequestParam("weekEndingDate") String weekEndingDate,
                                                                        @RequestParam("page") int page, @RequestParam("size") int size) {
        Response<MilestoneReadOnlyDto> response = new Response<>();
        response.setData(Arrays.asList(milestonesService.getMilestoneByProjectId(projectId, weekEndingDate, page, size)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for update Milestones related information.
     * @param milestoneAddDto is the data which needs to update.
     * @return response this class contains Milestones related data.
     * @throws ActionFailureException exception would be throws if Milestones data not found.
     */
    @EditAccess
    @PostMapping("/save-milestones")
    public ResponseEntity<Response<MilestoneReadOnlyDto>> updateMilestones(@RequestBody @Valid MilestoneAddDto milestoneAddDto) throws ActionFailureException {
        Response<MilestoneReadOnlyDto> response = new Response<>();
        milestonesService.saveMilestones(milestoneAddDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for getting the list of Milestones related information.
     * @return response this class contains Milestones related data.
     */
    @ViewAccess
    @GetMapping("/get-all-delivery-milestone-type")
    public ResponseEntity<Response<DeliveryMilestoneTypeDto>> getAllDeliveryMilestoneType(){
        Response<DeliveryMilestoneTypeDto> response = new Response<>();
        response.setData(milestonesService.getAllDeliveryMilestoneType());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
