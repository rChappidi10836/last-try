package com.gdp.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.ProjectFilterResponseDto;
import com.gdp.backend.dto.ProjectStatusResponseDto;
import com.gdp.backend.dto.SearchFilterDto;
import com.gdp.backend.service.ProjectFilterService;
import com.gdp.backend.service.ProjectStatusService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DashboardControllerTest {

    @InjectMocks
    DashboardController controllerUnderTest;

    @Mock
    ProjectStatusService projectStatusService;

    @Mock
    ProjectFilterService projectFilterService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void getProjectDetailsTest() throws Exception {
        ProjectStatusResponseDto projectStatusResponseDto = new ProjectStatusResponseDto();
        when(projectStatusService.getProjectStatusDefaultValue(anyInt(), anyInt() , anyString())).thenReturn(projectStatusResponseDto);
        this.mockMvc.perform(get("/dashboard/get-project-status-details?page=0&size=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectWithFilterCriteriaTest() throws Exception {
        SearchFilterDto searchFilterDto = new SearchFilterDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/dashboard/search-project-with-filter?page=0&size=1";
        String inputJson = objectMapper.writeValueAsString(searchFilterDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    /*@Test
    public void getFilterDropdownOptionsTest() throws Exception {
        ProjectFilterResponseDto projectFilterResponseDto = new ProjectFilterResponseDto();
        when(projectFilterService.getAllFiltersValue()).thenReturn(projectFilterResponseDto);
        this.mockMvc.perform(get("/dashboard/get-all-project-filter-details")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/
}
