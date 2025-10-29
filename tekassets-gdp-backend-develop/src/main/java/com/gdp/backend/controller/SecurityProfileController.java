package com.gdp.backend.controller;

import com.gdp.backend.annotations.EditAccess;
import com.gdp.backend.annotations.ViewAccess;
import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Utils;
import com.gdp.backend.domain.Response;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.service.RiskSurveyExportToExcelService;
import com.gdp.backend.service.SecurityProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

/**
 * This Controller class used for retrieving and saving the Security Profile related information and data.
 * @author gdp
 *
 */
@RestController
@RequestMapping("security-profile")
public class SecurityProfileController {

    @Autowired
    RiskSurveyExportToExcelService riskSurveyExportToExcelService;

    @Autowired
    SecurityProfileService securityProfileService;
    @Autowired
    private Utils utils;

    /**
     * This API would be use for getting Security Profile related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return response this class contains Security Profile related data.
     */
    @ViewAccess
    @GetMapping("/get-security-profile/{projectId}")
    public ResponseEntity<Response<SecurityProfileDto>> getSecurityProfileById(@PathVariable int projectId) {
        Response<SecurityProfileDto> response = new Response<>();
        if(utils.areOpcoIdsEqual(projectId)) {
            response.setData(securityProfileService.getSecurityProfileByProjectId(projectId));
            response.setStatusCode(EStatusCode.SUCCESS.name());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setStatusCode(EStatusCode.ERROR_ON_ID.name());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * This API would be use for getting Security Profile all answers related information and data.
     * @return response this class contains Security Profile answer related data.
     * @throws ActionFailureException exception would be throws if Security Profile data not found.
     */
    @ViewAccess
    @GetMapping("/get-security-profile-all-answers")
    public ResponseEntity<Response<SecurityProfileDto>> getSecurityProfileAllAnswers()
            throws ActionFailureException {
        Response<SecurityProfileDto> response = new Response<>();
        response.setData(securityProfileService.getAllAnswersForSecurityProfile());
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for getting Security Profile Report Log related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return response this class contains Security Profile Report Log related data.
     */
    @ViewAccess
    @GetMapping("/get-security-profile-report-log/{projectId}")
    public ResponseEntity<Response<SecurityProfileReportLog>> getSecurityProfileReportLogById(@PathVariable int projectId) {
        Response<SecurityProfileReportLog> response = new Response<>();
        response.setData(securityProfileService.getReportLogByProjectId(projectId));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for getting Security Profile Header related information and data using projectId.
     * @param projectId is the id of existing Project.
     * @return this class contains Security Profile Header related data.
     */
    @ViewAccess
    @GetMapping("/get-security-profile-header/{projectId}")
    public ResponseEntity<Response<SecurityProfileHeaderDto>> getSecurityProfileHeaderById(@PathVariable int projectId) {
        Response<SecurityProfileHeaderDto> response = new Response<>();
        response.setData(Arrays.asList(securityProfileService.getSecurityProfileHeaderByProjectId(projectId)));
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for saving and updating Security Profile related data.
     * @param securityProfileFormDataDto is the data needs to save or update.
     * @return this class contains Security Profile related data.
     * @throws ActionFailureException exception would be throws if Security Profile data not saved.
     */
    @EditAccess
    @PostMapping("/edit-security-profile")
    public ResponseEntity<Response> saveSecurityProfile(@RequestBody @Valid
                                                                SecurityProfileFormDataDto securityProfileFormDataDto) throws ActionFailureException {
        Response response = new Response<>();
        securityProfileService.saveSecurityProfile(securityProfileFormDataDto);
        response.setStatusCode(EStatusCode.SUCCESS.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This API would be use for getting Security Profile related data into the excel file using projectId.
     * @param projectId is the id of existing Project.
     * @param httpServletResponse is the interface to provide HTTP-specific functionality in sending a response.
     * @return this class contains Security Profile related data.
     * @throws ActionFailureException exception would be throws if Security Profile data not found.
     */
    @ViewAccess
    @GetMapping("/get-security-profile-excel/{projectId}")
    public ResponseEntity<InputStreamResource> getSecurityProfileDetailsInExcel(@PathVariable int projectId,
                                                                                HttpServletResponse httpServletResponse) throws ActionFailureException {
        ByteArrayInputStream inputStream = riskSurveyExportToExcelService.exportToExcelSecurityProfile(projectId,
                httpServletResponse, Constants.SECURITY_PROFILE);
        HttpHeaders headers = new HttpHeaders();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Security Profile.xlsx";
        headers.add(headerKey, headerValue);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(inputStream));
    }

    @ViewAccess
    @PostMapping("/map-Security-Profile/{projectId}")
    public ResponseEntity<Response> mapSecurityData(@PathVariable int projectId, @RequestBody @Valid SecurityProfileMappingResponseDto responseDto,
                                HttpServletResponse httpServletResponse)throws ActionFailureException {

        System.out.println(projectId + "raja" + responseDto.getCurrent_Username());



            Response response = new Response<>();
            securityProfileService.mappedsecuritydata(projectId, responseDto.getUnAnsweredlist(),responseDto.getCurrent_Username() );
            response.setStatusCode(EStatusCode.SUCCESS.name());
            return new ResponseEntity<>(response, HttpStatus.OK);


    }

}
