package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.Project;
import com.gdp.backend.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gdp.backend.common.Queries.GET_OPERATING_COMPANY_QUERY;

/**
 * This Service class used for creating the Project manually.
 * @author gdp
 *
 */
@Service
public class CreateProjectManuallyService {
    @Autowired
    EmailService emailService;

    @Autowired
    ProjectFilterService projectFilterService;

    @Autowired
    CreateAndEditProjectService createAndEditProjectService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectsRepository projectsRepository;

    /**
     * This Service would be use for the getting the Sales Organization related data.
     * @return this method would be return list of CreateProjectDropdownDto data.
     * @throws ActionFailureException exception would be throws if Sales Organization data not found.
     */
    public List<CreateProjectDropdownDto> getSalesOrganizationDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.SALES_ORGANIZATION_DROPDOWN, "SalesOrganizationDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Staffing Sales Region related data.
     * @return this method would be return list of CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Sales Organization Data is not found.
     */
    public List<CreateProjectDropdownDto> getStaffingSalesRegionDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.STAFFING_SALES_REGION_DROPDOWN, "StaffingSalesRegionDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This API would be use for the getting the Business Unit related data.
     * @return this method would be return list of CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Business Unit Data is not found.
     */
    public List<CreateProjectDropdownDto> getBusinessUnitDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.BUSINESS_UNIT_DROPDOWN, "BusinessUnitDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Practice Engagement related data.
     * @return this method would be return list of CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Practice Engagement Data is not found.
     */
    public List<CreateProjectDropdownDto> getPracticeEngagementDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.PRACTICE_ENGAGEMENT_DROPDOWN_DETAILS_SCREEN, "PracticeEngagementDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Delivery Model related data.
     * @return this class contains CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Practice Delivery Model is not found.
     */
    public List<CreateProjectDropdownDto> getDeliveryModelDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.DELIVERY_MODEL_DROPDOWN, "DeliveryModelDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Location Of Services related data.
     * @return this method would be return list of CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Location Of Services data is not found.
     */
    public List<CreateProjectDropdownDto> getLocationOfServicesDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.LOCATION_OF_SERVICES_DROPDOWN, "LocationOfServicesDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Contract Type related data.
     * @return this method would be return list of CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Contract Type data is not found.
     */
    public List<CreateProjectDropdownDto> getContractTypeDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.CONTRACT_TYPE_DROPDOWN, "ContractTypeDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Primary Service Type related data.
     * @return this method would be return list of CreateProjectDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Primary Service Type data is not found.
     */
    public List<CreateProjectDropdownDto> getPrimaryServiceTypeDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.PRIMARY_SERVICE_TYPE_DROPDOWN, "PrimaryServiceTypeDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the getting the Primary Resource Role related data.
     * @return this method would be return list of ResourceRoleDropdownDto Response.
     * @throws ActionFailureException exception would be throws, if Primary Resource Role data is not found.
     */
    public List<ResourceRoleDropdownDto> getResourceRoleDropdownValues() throws ActionFailureException {
        try {
            return entityManager.createNativeQuery(Queries.RESOURCE_ROLE_DROPDOWN, "ResourceRoleDropdownMapping").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    /**
     * This Service would be use for the Creating Project Manually.
     * @param createProjectFormDto this class use for the Creating Project Manually related data.
     * @param isSourceOpportunity this is check Opportunity.
     * @return this class contains CreateProjectFormDto Response.
     * @throws ActionFailureException exception would be throws, if Project data is not saved.
     */
    public CreateProjectFormDto addProject(CreateProjectFormDto createProjectFormDto, Boolean isSourceOpportunity) throws ActionFailureException {

        CreateProjectFormDto createdProject = createAndEditProjectService.getProjectFromDto(createProjectFormDto);
        //sendMail(createdProject, isSourceOpportunity);
        if(createProjectFormDto.getOpportunityId() != null && !createProjectFormDto.getOpportunityId().isEmpty()){
            sendMail(createdProject, isSourceOpportunity);
        }
        return createdProject;
    }

    /**
     * This method would be use for sending the Mail.
     * @param project is an entity class.
     * @param isSourceOpportunity is the boolean value.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void sendMail(CreateProjectFormDto project, boolean isSourceOpportunity) throws ActionFailureException {
        //boolean isSalesOrganisationEMEA =project.getSelectedSalesOrganizationId() == 1;
        System.out.println("sending mail");
        boolean isSalesOrganisationEMEA =false ;
        try {
            if(project.getSelectedSalesOrganizationId()!=null)
            {isSalesOrganisationEMEA = project.getSelectedSalesOrganizationId() == 1;}

            emailService.sendEmail(emailService.newProjectToMailDtoMapper(project), Constants.NEW_PROJECT, isSourceOpportunity, isSalesOrganisationEMEA);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_SEND_MAIL.name(), ex);
        }
    }


    /**
     * This method would be use for find the Project Details using ProjectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return CreateProjectFormDto.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public CreateProjectFormDto findProjectById(Integer projectId) throws ActionFailureException {
        Optional<Project> project = projectsRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
        try {
            return createAndEditProjectService.mapProjectDetailsToDto(project.get());
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }


    /**
     * This Service would be use for the Searching Project Resource and populating list of Resource data.
     * @param userSearchDto that needs to search.
     * @param pageNumber this is number of current page.
     * @param pageSize this is number of items which would be populate.
     * @return this class contains UserSearchResponseDto Response.
     * @throws ActionFailureException exception would be throws, if Searching Project Resource data is not found.
     */
    public UserSearchResponseDto getResourceData(UserSearchDto userSearchDto,String resourceOpcoID, int pageNumber, int pageSize) throws ActionFailureException {
        if (StringUtils.isEmpty(userSearchDto.getFirstName()) && StringUtils.isEmpty(userSearchDto.getLastName())
                && StringUtils.isEmpty(userSearchDto.getUserId())) {
            throw new ActionFailureException(Constants.BAD_REQUEST);
        }

        UserSearchResponseDto userSearchResponseDto = new UserSearchResponseDto();
        StringBuilder query = new StringBuilder();
        query.append(Queries.USER_SEARCH);
        String firstNameStartsWith = userSearchDto.getFirstName();
        String lastNameStartsWith = userSearchDto.getLastName();
        String userIdStartsWith = userSearchDto.getUserId();
        boolean isSecondSearchCriteria = false;
        if (!StringUtils.isEmpty(firstNameStartsWith)) {
            isSecondSearchCriteria = true;
            query.append(Constants.RESOURCE_FIRST_NAME + Constants.LIKE + firstNameStartsWith + Constants.PATTERN);
        }
        if (!StringUtils.isEmpty(lastNameStartsWith)) {
            if (isSecondSearchCriteria) {
                query.append(Constants.AND_CLAUSE);
            }
            isSecondSearchCriteria = true;
            query.append(Constants.RESOURCE_LAST_NAME + Constants.LIKE + lastNameStartsWith + Constants.PATTERN);
        }
        if (!StringUtils.isEmpty(userIdStartsWith)) {
            if (isSecondSearchCriteria) {
                query.append(Constants.AND_CLAUSE);
            }
            query.append(Constants.RESOURCE_USER_ID + Constants.LIKE + userIdStartsWith + Constants.PATTERN);
        }
        query.append(Constants.AND_CLAUSE);
        query.append(Constants.RESOURCE_OPCO);
        query.append(resourceOpcoID);
        Query finalQuery = entityManager.createNativeQuery(query.toString());
        userSearchResponseDto.setTotalNumberOfElements(finalQuery.getResultStream().count());
        finalQuery.setFirstResult((pageNumber) * pageSize);
        finalQuery.setMaxResults(pageSize);
        List<Object[]> searchedResourceList;
        try {
            searchedResourceList = finalQuery.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }

        if (CollectionUtils.isEmpty(searchedResourceList)) {
            throw new ActionFailureException(Constants.RECORD_NOT_FOUND);
        }

        List<UserSearchDto> userSearchDtoList = new ArrayList<>(searchedResourceList.size());
        for (Object[] row : searchedResourceList) {
            String cslName = projectFilterService.getCslName(row);
            userSearchDtoList.add(new UserSearchDto(row, cslName));
        }
        userSearchResponseDto.setUserSearchDtoList(userSearchDtoList);
        return userSearchResponseDto;
    }


    /**
     * This Service would be use for the getting Report Log Details data using ProjectId.
     * @param projectId use by class for getting the Project Details.
     * @return this class contains ReportLogDto Response.
     * @throws ActionFailureException exception would be throws, if Report Log Details data is not found.
     */
    public List<ReportLogDto> getReportLogByProjectId(int projectId) throws ActionFailureException {
        Query query = entityManager.createNativeQuery(Queries.REPORT_LOG_WITH_PROJECT_ID, "ReportLogMapping");
        query.setParameter(1, projectId);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
    }

    public List<OpcoDto> getOpcoList() throws ActionFailureException {
        List<OpcoDto> opcoList;
        try {
            opcoList = entityManager.createNativeQuery(GET_OPERATING_COMPANY_QUERY, "OpcoDto").getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        return opcoList;
    }



        public Boolean findProjectByName(String projectName) throws ActionFailureException{
            boolean project = projectsRepository.existsByProjectName(projectName);
            if (project) {
                return true;
            }else{
                return false;
            }

        }

}
