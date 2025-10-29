package com.gdp.backend.service;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ProjectStatusServiceTest {

    @InjectMocks
    ProjectStatusService serviceUnderTest;

    @Autowired
    EntityManager testEm;

    @Mock
    Query query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    void mockQuery(String name, List<Object> results) {
//        Query mockedQuery = mock(Query.class);
//        when(mockedQuery.getResultList()).thenReturn(results);
//        when(testEm.createNativeQuery(name)).thenReturn(results);
//        when(testEm.createNativeQuery(name)).thenReturn(query);
//
//    }
//
//    @Test
//    public void getProjectStatusDefaultValueTest() throws ActionFailureException {
//        mockQuery(Queries.PROJECT_STATUS_DEFAULT_QUERY, testObjectList());
//        when(testEm.createNativeQuery(anyString())).thenReturn(query);
//        EntityManager em = Mockito.mock(EntityManager.class);
//        when(em.createNativeQuery(Queries.PROJECT_STATUS_DEFAULT_QUERY)).thenReturn(query);
//        ProjectStatusResponseDto projectStatusResponseDto = serviceUnderTest.getProjectStatusDefaultValue(0,1);
//        assertEquals(projectStatusDtoList(), projectStatusResponseDto.getProjectStatusDtoList());
//    }

//    @Test
//    public void getCslNameFromQueryTest() {
//        Object[] testArray = new Object[3];
//        testArray[0] = new Object();
//        String cslFullName = serviceUnderTest.getCslFullName("Test", "CSL", "Name");
//        assertEquals(expectedCslName, cslFullName);
//    }

//    @Test
//    public void getCslFullNameWithMiddleNameTest() {
//        String expectedCslName = "Test CSL Name";
//        String cslFullName = serviceUnderTest.getCslFullName("Test", "CSL", "Name");
//        assertEquals(expectedCslName, cslFullName);
//    }
//
//    @Test
//    public void getCslFullNameWithoutMiddleNameTest() {
//        String expectedCslName = "Test Name";
//        String cslFullName = serviceUnderTest.getCslFullName("Test", null, "Name");
//        assertEquals(expectedCslName, cslFullName);
//    }
//
//    ProjectStatusResponseDto projectStatusResponseDto() {
//        ProjectStatusResponseDto projectStatusResponseDto = new ProjectStatusResponseDto();
//        projectStatusResponseDto.setTotalNumberOfElements(1L);
//        projectStatusResponseDto.setProjectStatusDtoList(projectStatusDtoList());
//        return projectStatusResponseDto;
//    }
//
//    List<ProjectStatusDto> projectStatusDtoList() {
//        ProjectStatusDto projectStatusDto = new ProjectStatusDto();
//        projectStatusDto.setProjectId(1);
//        projectStatusDto.setProjectName("Test Project");
//        projectStatusDto.setAccountName("Test Account");
//        projectStatusDto.setCsl("Test CSL");
//        return Arrays.asList(projectStatusDto);
//    }
//
//    List<Object> testObjectList() {
//        List<Object> objectList = new ArrayList<>();
//        objectList.add(1);
//        objectList.add("Test Project");
//        objectList.add("Test Account");
//        objectList.add("Test CSL");
//        return Arrays.asList(objectList);
//    }



}
