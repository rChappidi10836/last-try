package com.gdp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdp.backend.dto.*;
import com.gdp.backend.service.CreateProjectManuallyService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateProjectManuallyControllerTest {

    @InjectMocks
    CreateProjectManuallyController controllerUnderTest;

    @Mock
    CreateProjectManuallyService createProjectManuallyService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void getSalesOrganizationDropdownTest() throws Exception {
        when(createProjectManuallyService.getSalesOrganizationDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-sales-organization-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStaffingSalesRegionDropdownTest() throws Exception {
        when(createProjectManuallyService.getStaffingSalesRegionDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-staffing-sales-region-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBusinessUnitDropdownTest() throws Exception {
        when(createProjectManuallyService.getBusinessUnitDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-business-unit-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPracticeEngagementDropdownTest() throws Exception {
        when(createProjectManuallyService.getPracticeEngagementDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-practice-engagement-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getDeliveryModelDropdownTest() throws Exception {
        when(createProjectManuallyService.getDeliveryModelDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-delivery-model-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getLocationOfServicesDropdownTest() throws Exception {
        when(createProjectManuallyService.getLocationOfServicesDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-location-of-services-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getContractTypeDropdownTest() throws Exception {
        when(createProjectManuallyService.getContractTypeDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-contract-type-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPrimaryServiceTypeDropdownTest() throws Exception {
        when(createProjectManuallyService.getPrimaryServiceTypeDropdownValues()).thenReturn(getCreateProjectDropdownDtoList());
        this.mockMvc.perform(get("/create-project/get-primary-service-type-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getResourceRoleDropdownTest() throws Exception {
        ResourceRoleDropdownDto resourceRoleDropdownDto = new ResourceRoleDropdownDto();
        when(createProjectManuallyService.getResourceRoleDropdownValues()).thenReturn(Arrays.asList(resourceRoleDropdownDto));
        this.mockMvc.perform(get("/create-project/get-resource-role-dropdown")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createProjectManuallyTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/create-project/create-project-manually";
        String inputJson = objectMapper.writeValueAsString(createProjectFormDto());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void getResourceListTest() throws Exception {
        UserSearchDto userSearchDto = new UserSearchDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String uri = "/create-project/user-search?page=0&size=1";
        String inputJson = objectMapper.writeValueAsString(userSearchDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void getProjectDetailsByIdTest() throws Exception {
        CreateProjectFormDto createProjectFormDto = new CreateProjectFormDto();
        when(createProjectManuallyService.findProjectById(anyInt())).thenReturn(createProjectFormDto);
        this.mockMvc.perform(get("/create-project/find-project-by-id/{projectId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getReportLogByIdTest() throws Exception {
        when(createProjectManuallyService.getReportLogByProjectId(anyInt())).thenReturn(getReportLogDtoList());
        this.mockMvc.perform(get("/create-project/get-report-log/{projectId}", 1))
                .andExpect(status().isOk());
    }

    List<ReportLogDto> getReportLogDtoList() {
        ReportLogDto reportLogDto = new ReportLogDto();
        reportLogDto.setDateAndTime("2020-08-28 12:32 PM");
        reportLogDto.setLastUpdatedBy("Abhiruchi");
        return Arrays.asList(reportLogDto);
    }

    List<CreateProjectDropdownDto> getCreateProjectDropdownDtoList() {
        CreateProjectDropdownDto createProjectDropdownDto = new CreateProjectDropdownDto();
        createProjectDropdownDto.setId(1);
        createProjectDropdownDto.setName("Test");
        return Arrays.asList(createProjectDropdownDto);
    }

    CreateProjectFormDto createProjectFormDto() {
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
        addResourceDto.setResourceName("Test");
        addResourceDto.setResourceRoleName("Test Role");
        addResourceDto.setResourceId(1);
        addResourceDto.setResourceRoleId(1);
        return addResourceDto;
    }
}
