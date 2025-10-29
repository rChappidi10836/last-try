package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.*;
import com.gdp.backend.repository.*;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This Service class used for create and create and edit the Project Data.
 * @author gdp
 *
 */
@Service
public class CreateAndEditProjectService {

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    ProjectAccessHistoryRepository projectAccessHistoryRepository;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    ProjectOpportunityMapperRepository projectOpportunityMapperRepository;

    @Autowired
    ProjectResourceMapperRepository projectResourceMapperRepository;

    @Autowired
    ProjectPrimaryServiceTypeMapperRepository projectPrimaryServiceTypeMapperRepository;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectFilterService projectFilterService;

    @Autowired
    StatusReportService statusReportService;

    @Autowired
    ProjectLocationMapperRepository projectLocationMapperRepository;

    /**
     * This Service would be use for the getting the Project details from db.
     * @param createProjectFormDto this class use for the Creating Project related data.
     * @return this service returning the Project data.
     * @throws ActionFailureException exception would be throws, if Project data is not found.
     */
    @Transactional
    public CreateProjectFormDto getProjectFromDto(CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        Project project = new Project();
        List<Object> gdpIdList = entityManager.createNativeQuery(Queries.LAST_GDP_ID).getResultList();
        Long lastGdpIdValue = 0L;
        if (!CollectionUtils.isEmpty(gdpIdList)) {
            Object str = gdpIdList.get(0);
            if(str!=null) {
                String lastGdpId = String.valueOf(str);
                lastGdpIdValue = Long.parseLong(lastGdpId);
            }
        }
        lastGdpIdValue++;
        String unPadded = Long.toString(lastGdpIdValue);
        String padded = Constants.GDP_ID_PADDING.substring(unPadded.length()) + unPadded;


        project.setGdpId(padded);
        project.setFlex(createProjectFormDto.getIsFlex());
        project.setProjectName(createProjectFormDto.getProjectName());
        project.setStartDate(createProjectFormDto.getStartDate());
        project.setEndDate(createProjectFormDto.getEndDate());
        project.setSmpSite(createProjectFormDto.getLinkToSMPSite());
        project.setActive(true);
        project.setTargetTechnologyPlatform(createProjectFormDto.getTargetTechnologyPlatform());

        List<Client> providedactiveClient = clientRepository.findClientByOpco(createProjectFormDto.getAccountName(),createProjectFormDto.getSelectedOperatingCompanyId(),true);
        List<Client> providedinactiveClient = clientRepository.findClientByOpco(createProjectFormDto.getAccountName(),createProjectFormDto.getSelectedOperatingCompanyId(),false);
        if (!providedactiveClient.isEmpty()||!providedinactiveClient.isEmpty()) {
            if(!providedactiveClient.isEmpty()&&providedactiveClient.get(0).getOpcoid()==createProjectFormDto.getSelectedOperatingCompanyId()) {
                Client client = new Client();
                client.setId(providedactiveClient.get(0).getId());
                client.setClientName(createProjectFormDto.getAccountName());
                client.setActive(providedactiveClient.get(0).isActive());
                project.setClient(client);
            }
            if(providedactiveClient.isEmpty()&&providedinactiveClient.get(0).getOpcoid()==createProjectFormDto.getSelectedOperatingCompanyId()) {
                Client newClient = new Client();
                newClient.setClientName(createProjectFormDto.getAccountName());
                newClient.setActive(true);
                newClient.setOpcoid(createProjectFormDto.getSelectedOperatingCompanyId());
                try {
                    newClient = clientRepository.save(newClient);
                } catch (Exception ex) {
                    throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
                }
                project.setClient(newClient);
            }

        } else {
            Client newClient = new Client();
            newClient.setClientName(createProjectFormDto.getAccountName());
            newClient.setActive(true);
            newClient.setOpcoid(createProjectFormDto.getSelectedOperatingCompanyId());
            try {
                newClient = clientRepository.save(newClient);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
            project.setClient(newClient);
        }
        project = saveProject(setProjectValues(project, createProjectFormDto));
        addDummyProjectStatus(project);
        List<ProjectPrimaryServiceTypeMapper> addedPst = addProjectInPrimaryServiceTypeMapper(project, createProjectFormDto);
        List<ProjectResourceMapper> addedResource = addProjectInResourceMapper(project, createProjectFormDto);
        List<ProjectOpportunityMapper> opportunityIdList = addProjectInOpportunityMapper(project, createProjectFormDto);
        if (!CollectionUtils.isEmpty(opportunityIdList)) {
            updateHasProjectForOpportunity(createProjectFormDto.getOpportunityId(), true);
        }
        List<ProjectLocationMapper> addedLocation = addProjectInProjectLocationMapper(project, createProjectFormDto);
        updateAccessHistoryDetails(project.getId(), project.getLastModifiedByUser());
        return  getDtoFromProject(project, addedPst, addedResource, createProjectFormDto, opportunityIdList, addedLocation);
    }

    /**
     * This method would be use for the adding dummy Project status.
     * @param project is an entity class.
     * @throws ActionFailureException exception would be throws, if Project not found.
     */
    public void addDummyProjectStatus(Project project) throws ActionFailureException {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setProject(project);
        projectStatus.setActive(true);
        projectStatus.setWeekEndingDate(new Date(0));
        projectStatus.setOverAllStatus(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setSummary(Constants.NOT_APPLICABLE);
        projectStatus.setCurrentPhase(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setSchedule(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setCsat(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setBudget(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setProjectRisk(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setResources(Constants.DEFAULT_PROJECT_STATUS);
        projectStatus.setDummyRecord(true);
        projectStatus.setDeliveryRiskIndicator("Not Completed");
        projectStatus.setSecurityProfileStatus("Not Completed");
        statusReportService.saveProjectStatus(projectStatus);
    }


    /**
     * This method would be use for the saving the Project data.
     * @param project is an entity class.
     * @return this method is returning the project details once data saved successfully.
     * @throws ActionFailureException exception would be throws, if Project not saved.
     */
    @Transactional
    public Project saveProject(Project project) throws ActionFailureException {
        try {
            return projectsRepository.save(project);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This method would be use for the updating the Project details into the saveProject method.
     * @param project is an entity class.
     * @param createProjectFormDto this class use for the Creating Project related data.
     * @return this method would be return Project details.
     */
    public Project setProjectValues(Project project, CreateProjectFormDto createProjectFormDto) {
        if (!isEmpty(createProjectFormDto.getSelectedSalesOrganizationId())) {
            SalesOrganization salesOrganization = new SalesOrganization();
            salesOrganization.setId(createProjectFormDto.getSelectedSalesOrganizationId());
            project.setSalesOrganization(salesOrganization);
        }

        if (!isEmpty(createProjectFormDto.getSelectedStaffingSaleRegionId())) {
            StaffingSalesRegion staffingSalesRegion = new StaffingSalesRegion();
            staffingSalesRegion.setId(createProjectFormDto.getSelectedStaffingSaleRegionId());
            project.setStaffingSalesRegion(staffingSalesRegion);
        }

        if (!isEmpty(createProjectFormDto.getSelectedBusinessId())) {
            BusinessUnit businessUnit = new BusinessUnit();
            businessUnit.setId(createProjectFormDto.getSelectedBusinessId());
            project.setBusinessUnit(businessUnit);
        }

        if (!isEmpty(createProjectFormDto.getSelectedPracticeEngagementId())) {
            DeliveryOrganizationType deliveryOrganizationType = new DeliveryOrganizationType();
            deliveryOrganizationType.setId(createProjectFormDto.getSelectedPracticeEngagementId());
            project.setDeliveryOrganizationType(deliveryOrganizationType);
        }

        if (!isEmpty(createProjectFormDto.getSelectedDeliveryModelId())) {
            DeliveryModel deliveryModel = new DeliveryModel();
            deliveryModel.setId(createProjectFormDto.getSelectedDeliveryModelId());
            project.setDeliveryModel(deliveryModel);
        }

        if (!isEmpty(createProjectFormDto.getSelectedContractTypeId())) {
            ContractType contractType = new ContractType();
            contractType.setId(createProjectFormDto.getSelectedContractTypeId());
            project.setContractType(contractType);
        }
        return project;
    }

    /**
     * This method would be use for the updating the Project details for the Opportunity.
     * @param opportunityIdList is the list of Opportunity.
     * @param flag is the boolean value.
     * @throws ActionFailureException exception would be throws, if Project not found.
     */
    @Transactional
    public void updateHasProjectForOpportunity(String opportunityIdList, boolean flag) throws ActionFailureException {
        String[] opportunityId = opportunityIdList.split(",");
        for (String id : opportunityId) {
            Opportunity newOpportunity;
            String trimmedId = id.trim();
            try {
                newOpportunity = opportunityRepository.findByOpportunityId(trimmedId);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
            }
            if(newOpportunity==null) continue;
            newOpportunity.setHasProject(flag);
            try {
                opportunityRepository.save(newOpportunity);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
        }

    }

    /**
     * This Service would be use for saving the Project into the OpportunityMapper.
     * @param projectId is the id of existing Project.
     * @param opportunityIdList this is the list of opportunityId;
     * @return this method would be return list of opportunity details.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    @Transactional
    public List<ProjectOpportunityMapper> saveProjectInOpportunityMapper(Integer projectId, String opportunityIdList) throws ActionFailureException {
        List<ProjectOpportunityMapper> opportunityList = new ArrayList<>();
        String[] opportunityId = opportunityIdList.split(",");
        for (String id : opportunityId) {
            ProjectOpportunityMapper projectOpportunityMapper = new ProjectOpportunityMapper();
            Opportunity newOpportunity;
            String trimmedId = id.trim();
            newOpportunity = opportunityRepository.findByOpportunityId(trimmedId);
            if (isEmpty(newOpportunity)) {
                continue;
            }
            projectOpportunityMapper.setOpportunity(newOpportunity);
            Project newProject = new Project();
            newProject.setId(projectId);
            projectOpportunityMapper.setProject(newProject);
            try {
                opportunityList.add(projectOpportunityMapperRepository.save(projectOpportunityMapper));
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
        }
        return opportunityList;
    }

    /**
     * This Service would be use for adding the Project in OpportunityMapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the pojo class.
     * @return this method would be return list of opportunity details.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    @Transactional
    public List<ProjectOpportunityMapper> addProjectInOpportunityMapper(Project project, CreateProjectFormDto createProjectFormDto)
            throws ActionFailureException {
        List<ProjectOpportunityMapper> opportunityList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getOpportunityId())) {
            opportunityList = saveProjectInOpportunityMapper(project.getId(), createProjectFormDto.getOpportunityId());
        }
        return opportunityList;
    }

    /**
     * This Service would be use for getting the Project details from Primary Service type Mapper.
     * @param primaryServiceTypeId is the id of ServiceType.
     * @param projectId is the id of existing Project.
     * @return this method would be return primary ServiceTypeMapper.
     */
    public ProjectPrimaryServiceTypeMapper getProjectPrimaryServiceTypeMapper(Integer primaryServiceTypeId, Integer projectId) {
        ProjectPrimaryServiceTypeMapper primaryServiceTypeMapper = new ProjectPrimaryServiceTypeMapper();
        PrimaryServiceType primaryServiceType = new PrimaryServiceType();
        primaryServiceType.setId(primaryServiceTypeId);
        primaryServiceTypeMapper.setPrimaryServiceType(primaryServiceType);
        Project newProject = new Project();
        newProject.setId(projectId);
        primaryServiceTypeMapper.setProject(newProject);
        return primaryServiceTypeMapper;
    }

    /**
     * This Service would be use for getting the Project LocationMapper.
     * @param locationId is the id of existing Location Class.
     * @param projectId is the id of existing Project.
     * @return this method would be return project LocationMapper.
     */
    public ProjectLocationMapper getProjectLocationMapper(Integer locationId, Integer projectId){
        ProjectLocationMapper projectLocationMapper = new ProjectLocationMapper();
        Location location = new Location();
        location.setId(locationId);
        projectLocationMapper.setLocation(location);;
        Project newProject = new Project();
        newProject.setId(projectId);
        projectLocationMapper.setProject(newProject);
        return projectLocationMapper;
    }

    /**
     * This Service would be use for saving the Project Primary ServiceTypeMapper.
     * @param projectPrimaryServiceTypeMapper is the entity class.
     * @return this method would be return ProjectPrimaryServiceTypeMapper data.
     * @throws ActionFailureException exception would be throws if Project not found.
     */
    @Transactional
    public ProjectPrimaryServiceTypeMapper saveProjectPrimaryServiceTypeMapper
    (ProjectPrimaryServiceTypeMapper projectPrimaryServiceTypeMapper) throws ActionFailureException {
        try {
            return projectPrimaryServiceTypeMapperRepository.save(projectPrimaryServiceTypeMapper);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This Service would be use for saving the Project LocationMapper.
     * @param projectLocationMapper is the entity class.
     * @return this method would be return ProjectLocationMapper details.
     * @throws ActionFailureException exception would be throws if Project not found.
     */
    @Transactional
    public ProjectLocationMapper saveProjectLocationMapper(ProjectLocationMapper projectLocationMapper) throws ActionFailureException {
        try {
            return projectLocationMapperRepository.save(projectLocationMapper);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This Service would be use for adding the Project into the PrimaryServiceTypeMapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the Pojo class.
     * @return this method would be return Project PrimaryServiceTypeMapper.
     * @throws ActionFailureException exception would be throws if search data not found.
     */
    @Transactional
    public List<ProjectPrimaryServiceTypeMapper> addProjectInPrimaryServiceTypeMapper(Project project, CreateProjectFormDto createProjectFormDto)
            throws ActionFailureException {
        List<ProjectPrimaryServiceTypeMapper> pstList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getSelectedPrimaryServiceTypeId())) {
            for (Integer pstObject : createProjectFormDto.getSelectedPrimaryServiceTypeId()) {
                if(pstObject!=null)
                    pstList.add(saveProjectPrimaryServiceTypeMapper(getProjectPrimaryServiceTypeMapper(pstObject, project.getId())));
            }
        }
        return pstList;
    }

    /**
     * This method would be use for adding the Project into the ProjectLocationMapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the pojo class.
     * @return this method would be return locationMappers.
     * @throws ActionFailureException exception would be throws if project not found
     */

    @Transactional
    public List<ProjectLocationMapper> addProjectInProjectLocationMapper(Project project, CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        List<ProjectLocationMapper> locationMappers = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getSelectedLocationOfServicesId())) {
            for (Integer locObject : createProjectFormDto.getSelectedLocationOfServicesId()) {
                locationMappers.add(saveProjectLocationMapper(getProjectLocationMapper(locObject, project.getId())));
            }
        }
        return locationMappers;
    }


    /**
     * This method would be use for checking the Resource field is either empty or not.
     * @param addResourceDto is the pojo class.
     * @return this method would be return boolean value.
     */
    public boolean isResourceFieldEmpty(AddResourceDto addResourceDto) {
        return (isEmpty(addResourceDto.getResourceId()) || isEmpty(addResourceDto.getResourceRoleId()));
    }

    /**
     * This method would be use for getting ProjectResourceMapper.
     * @param projectId is the id of existing Project.
     * @param resourceId is the id of existing Resource.
     * @param resourceRoleId is the id of existing ResourceRole.
     * @return this method would be return project ResourceMapper.
     */
    public ProjectResourceMapper getProjectResourceMapper(Integer projectId, Integer resourceId,
                                                          Integer resourceRoleId) {
        ProjectResourceMapper projectResourceMapper = new ProjectResourceMapper();
        Project newProject = new Project();
        newProject.setId(projectId);
        projectResourceMapper.setProject(newProject);
        Resource resource = new Resource();
        resource.setId(resourceId);
        projectResourceMapper.setResource(resource);
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setId(resourceRoleId);
        projectResourceMapper.setResourceRole(resourceRole);
        return projectResourceMapper;
    }

    /**
     * This method would be use for saving the Project ResourceMapper.
     * @param projectResourceMapper is the entity class.
     * @return this method would be return ProjectResourceMapper.
     * @throws ActionFailureException exception would be throws if Project not found.
     */
    public ProjectResourceMapper saveProjectResourceMapper(ProjectResourceMapper projectResourceMapper) throws ActionFailureException {
        try {
            return projectResourceMapperRepository.save(projectResourceMapper);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }


    /**
     * This method would be use for adding the Project in the ResourceMapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the pojo class.
     * @return this method would be return addedResourceList.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    @Transactional
    public List<ProjectResourceMapper> addProjectInResourceMapper(Project project, CreateProjectFormDto createProjectFormDto)
            throws ActionFailureException {
        List<ProjectResourceMapper> addedResourceList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getManagerialInformation())) {
            for (AddResourceDto managerialInfo : createProjectFormDto.getManagerialInformation()) {
                if (!isResourceFieldEmpty(managerialInfo)) {
                    addedResourceList.add(saveProjectResourceMapper(getProjectResourceMapper(project.getId(), managerialInfo.getResourceId(), managerialInfo.getResourceRoleId())));
                }
            }
        }
        if(!isEmpty(createProjectFormDto.getOtherTeamMembers())){
            for(OtherTeamMemberDto otherTeamMemberDto: createProjectFormDto.getOtherTeamMembers()){
                try {
                    AddResourceDto resourceDto = otherTeamMemberDto.getAddResourceDto();
                    if(resourceDto.getResourceRoleId()==null) resourceDto.setResourceRoleId(otherTeamMemberDto.getRole());
                    projectResourceMapperRepository.markAsOtherTeamMember(project.getId(), resourceDto.getResourceId(), resourceDto.getResourceRoleId());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return addedResourceList;
    }


    /**
     * This method would be use for getting the data from the existing Project.
     * @param project is an entity class.
     * @param pst is the list of Project Primary ServiceTypeMapper.
     * @param resourceList is the list of Project ResourceMapper.
     * @param createProjectFormDto is the pojo class.
     * @param opportunityIdList is the list of Project OpportunityMapper.
     * @param locationMapperList is the list of Project LocationMapper.
     * @return this method would be return ProjectDto Object details.
     */
    public CreateProjectFormDto getDtoFromProject(Project project, List<ProjectPrimaryServiceTypeMapper> pst, List<ProjectResourceMapper> resourceList
            , CreateProjectFormDto createProjectFormDto, List<ProjectOpportunityMapper> opportunityIdList, List<ProjectLocationMapper> locationMapperList) throws ActionFailureException {
        CreateProjectFormDto newProjectDto = new CreateProjectFormDto();
        newProjectDto.setProjectId(project.getId());
        newProjectDto.setProjectName(project.getProjectName());
        newProjectDto.setStartDate(project.getStartDate());
        newProjectDto.setAccountName(createProjectFormDto.getAccountName());
        newProjectDto.setEndDate(project.getEndDate());
        newProjectDto.setGdpId(project.getGdpId());
        newProjectDto.setLinkToSMPSite(project.getSmpSite());
        newProjectDto.setModifiedBy(project.getLastModifiedByUser());

        if (!isEmpty(project.getSalesOrganization())) {
            newProjectDto.setSelectedSalesOrganizationId(project.getSalesOrganization().getId());
        }
        if (!isEmpty(project.getStaffingSalesRegion())) {
            newProjectDto.setSelectedStaffingSaleRegionId(project.getStaffingSalesRegion().getId());
        }
        if (!isEmpty(project.getBusinessUnit())) {
            newProjectDto.setSelectedBusinessId(project.getBusinessUnit().getId());
        }
        if (!isEmpty(project.getDeliveryOrganizationType())) {
            newProjectDto.setSelectedPracticeEngagementId(project.getDeliveryOrganizationType().getId());
        }
        if (!CollectionUtils.isEmpty(locationMapperList)) {
            newProjectDto.setSelectedLocationOfServicesId(createProjectFormDto.getSelectedLocationOfServicesId());
        }
        if (!isEmpty(project.getDeliveryModel())) {
            newProjectDto.setSelectedDeliveryModelId(project.getDeliveryModel().getId());
        }
        if (!isEmpty(project.getContractType())) {
            newProjectDto.setSelectedContractTypeId(project.getContractType().getId());
        }
        if (!CollectionUtils.isEmpty(resourceList)) {
            newProjectDto.setManagerialInformation(createProjectFormDto.getManagerialInformation());
        }
        if (!CollectionUtils.isEmpty(pst)) {
            newProjectDto.setSelectedPrimaryServiceTypeId(createProjectFormDto.getSelectedPrimaryServiceTypeId());
        }
        if (!CollectionUtils.isEmpty(opportunityIdList)) {
            newProjectDto.setOpportunityId(createProjectFormDto.getOpportunityId());
        }
        newProjectDto.setTargetTechnologyPlatform(createProjectFormDto.getTargetTechnologyPlatform());
        return newProjectDto;
    }

    /**
     * This Service would be use for the mapping the Project details with the dto.
     * @param project is an entity class.
     * @return this method would be return projectDetails.
     * @throws ActionFailureException exception would be throws if search data not found.
     */
    public CreateProjectFormDto mapProjectDetailsToDto(Project project) throws ActionFailureException {
        CreateProjectFormDto projectDetails = new CreateProjectFormDto();
        projectDetails.setProjectName(project.getProjectName());
        projectDetails.setIsFlex(project.getFlex());
        projectDetails.setStartDate(project.getStartDate());
        projectDetails.setEndDate(project.getEndDate());
        projectDetails.setGdpId(project.getGdpId());
        projectDetails.setLinkToSMPSite(project.getSmpSite());
        projectDetails.setPeopleSoftId(project.getPeopleSoftProjectId());
        projectDetails.setPeopleSoftEngagementId(project.getPeopleSoftEngagementId());

        if (!isEmpty(project.getClient())) {
            projectDetails.setAccountName(project.getClient().getClientName());
        }
        if (!isEmpty(project.getSalesOrganization())) {
            projectDetails.setSelectedSalesOrganizationId(project.getSalesOrganization().getId());
        }
        if (!isEmpty(project.getStaffingSalesRegion())) {
            projectDetails.setSelectedStaffingSaleRegionId(project.getStaffingSalesRegion().getId());
        }
        if (!isEmpty(project.getBusinessUnit())) {
            projectDetails.setSelectedBusinessId(project.getBusinessUnit().getId());
        }
        if (!isEmpty(project.getDeliveryOrganizationType())) {
            projectDetails.setSelectedPracticeEngagementId(project.getDeliveryOrganizationType().getId());
            projectDetails.setDeliveryOrganizationName(project.getDeliveryOrganizationType().getDeliveryOrganization());
        }
        if (!CollectionUtils.isEmpty(project.getProjectLocationMapperList())) {
            projectDetails.setSelectedLocationOfServicesId(getLocationIdsForProject(project));
        }
        if (!isEmpty(project.getDeliveryModel())) {
            projectDetails.setSelectedDeliveryModelId(project.getDeliveryModel().getId());
        }
        if (!isEmpty(project.getContractType())) {
            projectDetails.setSelectedContractTypeId(project.getContractType().getId());
        }

        if (!CollectionUtils.isEmpty(project.getProjectPrimaryServiceTypeMapperList())) {
            projectDetails.setSelectedPrimaryServiceTypeId(getPrimaryServiceTypeListFromProjectId(project));
        }
        if (!CollectionUtils.isEmpty(project.getProjectOpportunityMapperList())) {
            projectDetails.setOpportunityId(getOpportunityIdForProject(project));
        }
            projectDetails.setTargetTechnologyPlatform(getTargetTechologyPlatform(projectDetails.getOpportunityId(),project.getTargetTechnologyPlatform()));

        if (!CollectionUtils.isEmpty(project.getProjectResourceMapperList())) {
            projectDetails.setManagerialInformation(getResourceListByProjectId(project.getId()));
        }
        projectDetails.setClosed(!project.isActive()); // if project is in active then it's closed.
        return projectDetails;
    }

    public String getTargetTechologyPlatform(String OpportunityList, String TTPList) throws ActionFailureException {
//           Map<String,String> oppTTPMap=new  HashMap<String,String>();
           String oppTTP="";
        if((TTPList==""||TTPList==null|| (TTPList!=null && TTPList!="" && TTPList.isEmpty()))&&(OpportunityList!=null&&OpportunityList!=""&&!OpportunityList.isEmpty())){
            TTPList="";
            for (String i : OpportunityList.split(",")) {
                String TTP="";
                try {
                    TTP = opportunityRepository.getTargetTechnologyPlatform(i.trim());
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(TTP!=null&&(TTPList==null||TTPList=="")) {
                    TTPList=TTP;
                }else if(TTPList!=null && TTPList!= ""){
                    TTPList=TTPList + ", "+ TTP;
                }
            }
        }
        return TTPList;
    }




    /**
     * This Service would be use for the getting the list of PrimaryServiceType using ProjectId.
     * @param project is an entity class.
     * @return this method would be return list of pstId.
     */
    public List<Integer> getPrimaryServiceTypeListFromProjectId(Project project) {
        List<Integer> pstIdList = new ArrayList<>();
        for (ProjectPrimaryServiceTypeMapper entry : project.getProjectPrimaryServiceTypeMapperList()) {
            pstIdList.add(entry.getPrimaryServiceType().getId());
        }
        return pstIdList;
    }

    /**
     * This method would be use for getting the list of Resource data using ProjectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return list of AddResourceDto.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public List<AddResourceDto> getResourceListByProjectId(Integer projectId) throws ActionFailureException {
        Query query = entityManager.createNativeQuery(Queries.RESOURCES_WITH_PROJECT_ID);
        query.setParameter(1, projectId);
        List<Object[]> resourceList;
        try {
            resourceList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<AddResourceDto> managerialInformationList = new ArrayList<>(resourceList.size());
        for (Object[] row : resourceList) {
            String cslName = projectFilterService.getCslName(row);
            managerialInformationList.add(new AddResourceDto(row, cslName));
        }
        return managerialInformationList;
    }

    /**
     * This method would be use for updating access history details of Project.
     * @param projectId is the id of existing Project.
     * @param modifiedBy is updating the last modifiedBy User.
     * @throws ActionFailureException exception would be throws if invalid projectId.
     */
    public void updateAccessHistoryDetails(Integer projectId, String modifiedBy) throws ActionFailureException {
        ProjectAccessHistory projectAccessHistory = new ProjectAccessHistory();
        projectAccessHistory.setProjectId(projectId);
        projectAccessHistory.setModifiedBy(CurrentUsernameUtil.getCurrentUsername());
        projectAccessHistory.setModifiedAt(new Date());
        try {
            projectAccessHistoryRepository.save(projectAccessHistory);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This method would be use for getting the Project details using ProjectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return Project details.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public Project getProjectById(int projectId) throws ActionFailureException {
        Optional<Project> optionalProject = projectsRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
        return optionalProject.get();
    }

    /**
     * This Service would be use for editing and updating the existing Project details.
     * @param createProjectFormDto is the data which we need to update.
     * @return this class contains Project related data.
     * @throws ActionFailureException exception would be throws if Project not found.
     */
    @Transactional
    public CreateProjectFormDto updateProjectDetails(CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        Project project = getProjectById(createProjectFormDto.getProjectId());
        project.setFlex(createProjectFormDto.getIsFlex());

        if (!isEmpty(createProjectFormDto.getProjectName()) && !createProjectFormDto.getProjectName().equals(project.getProjectName())) {
            project.setProjectName(createProjectFormDto.getProjectName());
        }
        if (!isEmpty(createProjectFormDto.getStartDate()) && !createProjectFormDto.getStartDate().equals(project.getStartDate())) {
            project.setStartDate(createProjectFormDto.getStartDate());
        }
        if (!isEmpty(createProjectFormDto.getEndDate()) && !createProjectFormDto.getEndDate().equals(project.getEndDate())) {
            project.setEndDate(createProjectFormDto.getEndDate());
        }
        project.setTargetTechnologyPlatform(createProjectFormDto.getTargetTechnologyPlatform());
        project.setSmpSite(createProjectFormDto.getLinkToSMPSite());
        project = saveProject(setProjectValues(project, createProjectFormDto));
        List<ProjectPrimaryServiceTypeMapper> addedPst = updateProjectPrimaryServiceTypeMapper(project, createProjectFormDto);
        List<ProjectResourceMapper> addedResource = updateProjectResourceMapper(project, createProjectFormDto);
        List<ProjectOpportunityMapper> opportunityIdList = updateProjectOpportunityMapper(project, createProjectFormDto);
        List<ProjectLocationMapper> locationIdList = updateProjectLocationMapper(project, createProjectFormDto);
        if (!CollectionUtils.isEmpty(opportunityIdList)) {
            updateHasProjectForOpportunity(createProjectFormDto.getOpportunityId(), true);
        }
        updateAccessHistoryDetails(createProjectFormDto.getProjectId(), project.getLastModifiedByUser());
        return getDtoFromProject(project, addedPst, addedResource, createProjectFormDto, opportunityIdList, locationIdList);
    }

    /**
     * This method would be use for deleting the Project from PrimaryServiceTypeMapper using projectId.
     * @param projectId is the id of existing Project.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void deleteProjectFromPrimaryServiceTypeMapper(Integer projectId) throws ActionFailureException {
        try {
            transactionTemplate.execute(transactionStatus -> {
                projectPrimaryServiceTypeMapperRepository.deleteByProjectId(projectId);
                transactionStatus.flush();
                return null;
            });
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
    }

    /**
     * This method would be use for deleting the Project from FromLocationMapper using projectId.
     * @param projectId is the id of existing Project.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void deleteProjectFromLocationMapper(Integer projectId) throws ActionFailureException {
        try {
            transactionTemplate.execute(transactionStatus -> {
                projectLocationMapperRepository.deleteByProjectId(projectId);
                transactionStatus.flush();
                return null;
            });
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
    }

    /**
     * This method would be use for updating the Project PrimaryServiceTypeMapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the pojo class.
     * @return this method would be return list of ProjectPrimaryServiceTypeMapper.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    @Transactional
    public List<ProjectPrimaryServiceTypeMapper> updateProjectPrimaryServiceTypeMapper(Project project, CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        List<ProjectPrimaryServiceTypeMapper> projectPrimaryServiceTypeMapperList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getSelectedPrimaryServiceTypeId()) && !createProjectFormDto.getSelectedPrimaryServiceTypeId().equals(getPrimaryServiceTypeListFromProjectId(project))) {
            deleteProjectFromPrimaryServiceTypeMapper(createProjectFormDto.getProjectId());
            projectPrimaryServiceTypeMapperList = addProjectInPrimaryServiceTypeMapper(project, createProjectFormDto);
        }
        return projectPrimaryServiceTypeMapperList;
    }

    /**
     * This method would be use for updating the Project LocationMapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the pojo class.
     * @return this method would be return list of locationMapper.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    @Transactional
    public List<ProjectLocationMapper> updateProjectLocationMapper(Project project, CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        List<ProjectLocationMapper> locationMapperList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getSelectedLocationOfServicesId()) && !createProjectFormDto.getSelectedLocationOfServicesId().equals(getLocationIdsForProject(project))) {
            deleteProjectFromLocationMapper(createProjectFormDto.getProjectId());
            locationMapperList = addProjectInProjectLocationMapper(project, createProjectFormDto);
        }
        return locationMapperList;
    }

    /**
     * This method would be use for deleting the Project from OpportunityMapper.
     * @param project is an entity class.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void deleteProjectFromOpportunityMapper(Project project) throws ActionFailureException {
        try {
            transactionTemplate.execute(transactionStatus -> {
                projectOpportunityMapperRepository.deleteByProjectId(project.getId());
                transactionStatus.flush();
                return null;
            });
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
    }

    /**
     * This method would be use for getting the OpportunityId Using Project Details.
     * @param project is an entity class.
     * @return this method would be return OpportunityId.
     */
    public String getOpportunityIdForProject(Project project) {
        StringBuilder opportunityIdList = new StringBuilder();
        for (ProjectOpportunityMapper entry : project.getProjectOpportunityMapperList()) {
            opportunityIdList.append(Constants.SEPARATOR);
            opportunityIdList.append(entry.getOpportunity().getOpportunityId());

        }
        if(opportunityIdList.length()>1)
            return (opportunityIdList.substring(1));
        return "";
    }


    /**
     * This method would be use for getting the LocationId Using Project Details.
     * @param project is an entity class.
     * @return this method would be return list of LocationId.
     */
    public List<Integer> getLocationIdsForProject(Project project) {
        List<Integer> locationIdList = new ArrayList<>();
        for (ProjectLocationMapper entry : project.getProjectLocationMapperList()) {
            locationIdList.add(entry.getLocation().getId());
        }
        return locationIdList;
    }

    /**
     * This Service would be use for updating and mapping the Project with Opportunity.
     * @param project is an entity class.
     * @param createProjectFormDto is the data which we need to update.
     * @return this method would be return opportunityMapperList.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    @Transactional
    public List<ProjectOpportunityMapper> updateProjectOpportunityMapper(Project project, CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        List<ProjectOpportunityMapper> opportunityMapperList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getOpportunityId()) && !createProjectFormDto.getOpportunityId().equals(getOpportunityIdForProject(project))) {
            deleteProjectFromOpportunityMapper(project);
            opportunityMapperList = addProjectInOpportunityMapper(project, createProjectFormDto);
        }
        return opportunityMapperList;
    }

    /**
     * This method would be use for deleting the Project details from ProjectResourceMapper using projectId.
     * @param projectId is the id of existing Project.
     * @throws ActionFailureException exception would be throws if project not found.
     */
    public void deleteProjectFromProjectResourceMapper(Integer projectId) throws ActionFailureException {
        try {
            transactionTemplate.execute(transactionStatus -> {
                projectResourceMapperRepository.deleteByProjectId(projectId);
                transactionStatus.flush();
                return null;
            });
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
    }

    /**
     * This Service would be use for updating the existing Project Resource Mapper.
     * @param project is an entity class.
     * @param createProjectFormDto is the data which we need to update.
     * @return project Resource Mapper List would be return.
     * @throws ActionFailureException exception would be throws if Project not found.
     */
    @Transactional
    public List<ProjectResourceMapper> updateProjectResourceMapper(Project project, CreateProjectFormDto createProjectFormDto) throws ActionFailureException {
        List<ProjectResourceMapper> projectResourceMapperList = new ArrayList<>();
        if (!isEmpty(createProjectFormDto.getManagerialInformation()) && !createProjectFormDto.getManagerialInformation().equals(getResourceListByProjectId(project.getId()))) {
            deleteProjectFromProjectResourceMapper(createProjectFormDto.getProjectId());
            projectResourceMapperList = addProjectInResourceMapper(project, createProjectFormDto);
        }
        return projectResourceMapperList;
    }



}
