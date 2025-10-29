package com.gdp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.AddResourceDto;
import com.gdp.backend.dto.CreateProjectFormDto;
import com.gdp.backend.service.CreateAndEditProjectService;
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
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EditProjectControllerTest {

    @InjectMocks
    EditProjectController controllerUnderTest;

    @Mock
    CreateAndEditProjectService createAndEditProjectService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void editProjectDetailsTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/edit-project/edit-project-details";
        String inputJson = objectMapper.writeValueAsString(getCreateProjectFormDto());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    CreateProjectFormDto getCreateProjectFormDto() {
        CreateProjectFormDto createProjectFormDto = new CreateProjectFormDto();
        createProjectFormDto.setProjectName("Project");
        createProjectFormDto.setAccountName("Client");
        createProjectFormDto.setStartDate(new Date());
        createProjectFormDto.setEndDate(new Date());
        createProjectFormDto.setSelectedPracticeEngagementId(1);
        createProjectFormDto.setSelectedDeliveryModelId(1);
        createProjectFormDto.setManagerialInformation(Arrays.asList(addResourceDto()));
        return createProjectFormDto;
    }

    AddResourceDto addResourceDto() {
        AddResourceDto addResourceDto = new AddResourceDto();
        addResourceDto.setResourceName("Test Resource");
        addResourceDto.setResourceRoleName("Test Role");
        addResourceDto.setResourceId(1);
        addResourceDto.setResourceRoleId(1);
        return addResourceDto;
    }

}
