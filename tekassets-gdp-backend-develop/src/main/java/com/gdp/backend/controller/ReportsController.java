package com.gdp.backend.controller;

import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * This Controller class used for retrieving the Project Reports related information.
 * @author gdp
 *
 */
@RestController
@RequestMapping("reports")
@Secured({"ROLE_Admin", "ROLE_Global Edit Access", "ROLE_Global View Access","ROLE_Executive"})
public class ReportsController {
    private final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    @Autowired
    ExcelService excelService;

    /**
     * This API would be use for download the Project related information and data.
     * @param isActive is the boolean value which check the project is active or not.
     * @param response this class contains Project related data.
     * @return HttpStatus.
     * @throws ActionFailureException exception would be throws if Project data not found.
     */
    @GetMapping("/project-data")
    public ResponseEntity<StreamingResponseBody> downloadProjectData(@RequestParam("excludeInActive") boolean isActive,@RequestParam("opcoId") String opcoID, HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createProjectReportData(outputStream, isActive,opcoID);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Engagement Data Export - " + (isActive ? "Active" : "All") + ".xlsx")
                .body(bb);
    }

    /**
     * This API would be use for download the Delivery Milestones related information and data.
     * @param isActive is the boolean value which check the project is active or not.
     * @param response this class contains Delivery Milestones related data.
     * @return HttpStatus.
     * @throws ActionFailureException exception would be throws if Delivery Milestones data not found.
     */
    @GetMapping("/milestone-delivery-report")
    public ResponseEntity<StreamingResponseBody> downloadDeliveryMilestones(@RequestParam("onlyActiveStatus") boolean isActive,@RequestParam("opcoId") String opcoID, HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createMilestoneDeliveryReport(outputStream, isActive,opcoID);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Milestone Delivery Report - " + (isActive ? "Active" : "All") + ".xlsx")
                .body(bb);
    }

    /**
     * This API would be use for download the Agile Milestones related information and data.
     * @param isActive is the boolean value which check the project is active or not.
     * @param response this class contains Agile Milestones related data.
     * @return HttpStatus.
     * @throws ActionFailureException exception would be throws if Agile Milestones data not found.
     */
    @GetMapping("/milestone-agile-sprint-report")
    public ResponseEntity<StreamingResponseBody> downloadAgileMilestone(@RequestParam("onlyActiveStatus") boolean isActive,@RequestParam("opcoId") String opcoID, HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createMilestoneAgileSprintReport(outputStream, isActive,opcoID);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Milestone Agile Sprint Report - " + (isActive ? "Active" : "All") + ".xlsx")
                .body(bb);
    }

    /**
     * This API would be use for download the Security Profile related information and data.
     * @param isActive is the boolean value which check the project is active or not.
     * @param response this class contains Security Profile related data.
     * @return HttpStatus.
     * @throws ActionFailureException exception would be throws if Security Profile data not found.
     */
    @GetMapping("/security-profile-report")
    public ResponseEntity<StreamingResponseBody> downloadSecurityProfile(@RequestParam("onlyActiveStatus") boolean isActive, @RequestParam("opcoId") String opcoID,HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createSecurityProfileReport(outputStream, isActive,opcoID);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "TGS IS Security Profile Report - " + (isActive ? "Active" : "All") + ".xlsx")
                .body(bb);
    }

    /**
     * This API would be use for download the PMLC related information and data.
     * @param response this class contains PMLC related data.
     * @return HttpStatus.
     * @throws ActionFailureException exception would be throws if PMLC data not found.
     */
    @GetMapping("/pmlc-scorecard-report")
    public ResponseEntity<StreamingResponseBody> downloadPMLC(@RequestParam("opcoId") String opcoID,final HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {

                excelService.createPMLCScorecardReport(outputStream,opcoID);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PMO Compliance Report.xlsx")
                .body(bb);
    }
    @GetMapping("/all-stakeholders-data")
    public ResponseEntity<StreamingResponseBody> downloadStakeholdersData(@RequestParam("excludeInActive") boolean isActive,@RequestParam("opcoId") String opcoID, HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createStakeHoldersReportData(outputStream, isActive,opcoID);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "StakeHolders Data Export - " + (isActive ? "Active" : "All") + ".xlsx")
                .body(bb);
    }

    @GetMapping("/trend-report")
    public ResponseEntity<StreamingResponseBody> downloadTrendReport(@RequestParam("excludeInActive") boolean isActive,
                                                                     @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromWeekEndDate,
                                                                     @RequestParam("nextDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date nextWeekEndDate,
                                                                     @RequestParam("opcoId")   String opcoId,
                                                                     HttpServletResponse response) throws ActionFailureException {
        StreamingResponseBody bb = outputStream -> {
            try {
                excelService.createTrendReportData(outputStream, isActive,fromWeekEndDate,nextWeekEndDate,opcoId);
            } catch (ActionFailureException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        };
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Trend Report Export - " + (isActive ? "Active" : "All") + ".xlsx")
                .body(bb);
    }
}
