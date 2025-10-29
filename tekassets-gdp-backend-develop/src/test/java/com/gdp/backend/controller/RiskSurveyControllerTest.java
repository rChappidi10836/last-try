package com.gdp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.ReportLogDto;
import com.gdp.backend.dto.RiskSurveyDto;
import com.gdp.backend.service.RiskSurveyAccessHistoryService;
import com.gdp.backend.service.RiskSurveyService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RiskSurveyControllerTest {

    @InjectMocks
    RiskSurveyController controllerUnderTest;

    @Mock
    RiskSurveyService riskSurveyService;

    @Mock
    RiskSurveyAccessHistoryService riskSurveyAccessHistoryService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void saveRiskSurveyFormTest() throws Exception {
        RiskSurveyDto riskSurveyDto = new RiskSurveyDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/risk-survey/save-risk-survey";
        String inputJson = objectMapper.writeValueAsString(riskSurveyDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void getRiskSurveyTest() throws Exception {
        RiskSurveyDto riskSurveyDto = new RiskSurveyDto();
        when(riskSurveyService.getRiskSurvey(anyInt())).thenReturn(riskSurveyDto);
        this.mockMvc.perform(get("/risk-survey/get-risk-survey/{projectId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getRiskSurveyReportLogTest() throws Exception {
        when(riskSurveyAccessHistoryService.getRiskSurveyReportLogByProjectId(anyInt())).thenReturn(getReportLogDtoList());
        this.mockMvc.perform(get("/risk-survey/get-risk-survey-report-log/{projectId}", 1))
                .andExpect(status().isOk());
    }

    List<ReportLogDto> getReportLogDtoList() {
        ReportLogDto reportLogDto = new ReportLogDto();
        reportLogDto.setDateAndTime("2020-08-28 12:32 PM");
        reportLogDto.setLastUpdatedBy("Abhiruchi");
        return Arrays.asList(reportLogDto);
    }

}
