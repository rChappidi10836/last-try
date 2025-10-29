package com.gdp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.SecurityProfileDto;
import com.gdp.backend.dto.SecurityProfileFormDataDto;
import com.gdp.backend.dto.SecurityProfileHeaderDto;
import com.gdp.backend.dto.SecurityProfileReportLog;
import com.gdp.backend.service.RiskSurveyExportToExcelService;
import com.gdp.backend.service.SecurityProfileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityProfileControllerTest {

    @InjectMocks
    SecurityProfileController controllerUnderTest;

    @Mock
    SecurityProfileService securityProfileService;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    RiskSurveyExportToExcelService riskSurveyExportToExcelService;


    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void getSecurityProfileByIdTest() throws Exception {
        SecurityProfileDto securityProfileDto = new SecurityProfileDto();
        when(securityProfileService.getSecurityProfileByProjectId(anyInt())).thenReturn(Arrays.asList(securityProfileDto));
        this.mockMvc.perform(get("/security-profile/get-security-profile/{projectId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getSecurityProfileAllAnswersTest() throws Exception {
        SecurityProfileDto securityProfileDto = new SecurityProfileDto();
        when(securityProfileService.getAllAnswersForSecurityProfile()).thenReturn(Arrays.asList(securityProfileDto));
        this.mockMvc.perform(get("/security-profile/get-security-profile-all-answers"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSecurityProfileReportLogByIdTest() throws Exception {
        SecurityProfileReportLog securityProfileReportLog = new SecurityProfileReportLog();
        when(securityProfileService.getReportLogByProjectId(anyInt())).thenReturn(Arrays.asList(securityProfileReportLog));
        this.mockMvc.perform(get("/security-profile/get-security-profile-report-log/{projectId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getSecurityProfileHeaderByIdTest() throws Exception {
        SecurityProfileHeaderDto securityProfileHeaderDto = new SecurityProfileHeaderDto();
        when(securityProfileService.getSecurityProfileHeaderByProjectId(anyInt())).thenReturn(securityProfileHeaderDto);
        this.mockMvc.perform(get("/security-profile/get-security-profile-header/{projectId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void saveSecurityProfileTest() throws Exception {
        SecurityProfileFormDataDto securityProfileFormDataDto = new SecurityProfileFormDataDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/security-profile/edit-security-profile";
        String inputJson = objectMapper.writeValueAsString(securityProfileFormDataDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

//    @Test
//    public void getSecurityProfileDetailsInExcelTest() throws Exception {
//        File file = ResourceUtils.getFile("classpath:excel-files/Risk-survey.xlsx");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        MvcResult mvcResult = this.mockMvc.perform(get("/security-profile/get-security-profile-excel/{projectId}", 1, httpServletResponse, Constants.SECURITY_PROFILE))
//                .andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//    }


}
