package com.gdp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.StatusReportCreateNewDto;
import com.gdp.backend.dto.StatusReportDto;
import com.gdp.backend.service.StatusReportService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatusReportControllerTest {

    @InjectMocks
    StatusReportController controllerUnderTest;

    @Mock
    StatusReportService statusReportService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void getStatusReportTest() throws Exception {
        StatusReportDto statusReportDto = new StatusReportDto();
        when(statusReportService.getStatusReportByProjectId(anyInt(), anyInt(), anyInt())).thenReturn(statusReportDto);
        this.mockMvc.perform(get("/status-report/get-status-report-by-projectId/{projectId}?page=0&size=1", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void saveStatusReportTest() throws Exception {
        StatusReportCreateNewDto statusReportCreateNewDto = new StatusReportCreateNewDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/status-report/save-status-report";
        String inputJson = objectMapper.writeValueAsString(statusReportCreateNewDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void updateStatusReportTest() throws Exception {
        StatusReportCreateNewDto statusReportCreateNewDto = new StatusReportCreateNewDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/status-report/update-status-report";
        String inputJson = objectMapper.writeValueAsString(statusReportCreateNewDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteStatusReportTest() throws Exception {
        StatusReportCreateNewDto statusReportCreateNewDto = new StatusReportCreateNewDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/status-report/delete-status-report";
        String inputJson = objectMapper.writeValueAsString(statusReportCreateNewDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
