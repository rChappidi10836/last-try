package com.gdp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.AttachToExistingProjectDto;
import com.gdp.backend.service.AttachToExistingProjectService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttachToExistingProjectControllerTest {

    @InjectMocks
    AttachToExistingProjectController controllerUnderTest;

    @Mock
    AttachToExistingProjectService attachToExistingProjectService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void getAllProjectDetailsTest() throws Exception {
        when(attachToExistingProjectService.getAllProjectAndClient()).thenReturn(getAttachToExistingProjectDtoList());
        this.mockMvc.perform(get("/attach-opportunity/get-all-project-details")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void attachOpportunityToProjectTest() throws Exception {
        AttachToExistingProjectDto attachToExistingProjectDto = new AttachToExistingProjectDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/attach-opportunity/add-opportunity-to-project";
        String inputJson = objectMapper.writeValueAsString(attachToExistingProjectDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    List<AttachToExistingProjectDto> getAttachToExistingProjectDtoList() {
        AttachToExistingProjectDto attachToExistingProjectDto = new AttachToExistingProjectDto();
        attachToExistingProjectDto.setProjectId(1);
        attachToExistingProjectDto.setProjectName("Test Project");
        attachToExistingProjectDto.setClientId(1);
        attachToExistingProjectDto.setClientName("Test Client");
        return Arrays.asList(attachToExistingProjectDto);
    }
}
