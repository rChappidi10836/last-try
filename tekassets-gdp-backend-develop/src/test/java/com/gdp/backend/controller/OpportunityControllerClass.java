package com.gdp.backend.controller;

import com.gdp.backend.dto.OpportunityResponseDto;
import com.gdp.backend.service.OpportunityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OpportunityControllerClass {

    @InjectMocks
    OpportunityController controllerUnderTest;

    @Mock
    OpportunityService opportunityService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void getOpportunityDetailsTest() throws Exception {
        OpportunityResponseDto opportunityResponseDto = new OpportunityResponseDto();
        when(opportunityService.getAllOpportunityValues(anyInt(), anyInt())).thenReturn(opportunityResponseDto);
        this.mockMvc.perform(get("/opportunity/get-all-opportunity-details?page=0&size=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
