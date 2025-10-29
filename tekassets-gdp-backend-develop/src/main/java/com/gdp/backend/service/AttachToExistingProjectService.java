package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.AttachToExistingProjectDto;
import com.gdp.backend.dto.CreateProjectFormDto;
import com.gdp.backend.dto.MailRequestDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.Opportunity;
import com.gdp.backend.model.Project;
import com.gdp.backend.model.ProjectOpportunityMapper;
import com.gdp.backend.repository.OpportunityRepository;
import com.gdp.backend.repository.ProjectOpportunityMapperRepository;
import com.gdp.backend.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * This Service class used for Attaching to existing Project and also used for retrieving the complete data info about the Projects and opportunity.
 * @author gdp
 *
 */
@Service
public class AttachToExistingProjectService {

    @Autowired
    EmailService emailService;

    @Autowired
    CreateAndEditProjectService createAndEditProjectService;

    @Autowired
    CreateProjectManuallyService createProjectManuallyService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ProjectOpportunityMapperRepository projectOpportunityMapperRepository;

    @Autowired
    OpportunityRepository opportunityRepository;

    /**
     * This Service would be use for the getting the list of Project details and Client details as well.
     * @return this service would be return to list of existing Project details.
     * @throws ActionFailureException exception would be throws, if Project Data is not found.
     */
    public List<AttachToExistingProjectDto> getAllProjectAndClient() throws ActionFailureException {
        Query query = entityManager.createNativeQuery(Queries.ATTACH_OPPORTUNITY_DETAILS);
        List<Object[]> allDetailsList;
        try {
            allDetailsList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<AttachToExistingProjectDto> attachToExistingProjectDtoList = new ArrayList<>(allDetailsList.size());
        for (Object[] row : allDetailsList) {
            attachToExistingProjectDtoList.add(AttachToExistingProjectDto.mapToAttachToExistingProjectDto(row));
        }
        return attachToExistingProjectDtoList;
    }

    /**
     * This Service would be use for the attaching Opportunity to Project and Mapping the data.
     * @param attachToExistingProjectDto is using parameter AttachToExistingProjectDto.
     * @throws ActionFailureException exception would be throws, if Opportunity Data is not found.
     */
    @Transactional
    public void addOpportunityToProject(AttachToExistingProjectDto attachToExistingProjectDto) throws ActionFailureException {
        ProjectOpportunityMapper projectOpportunityMapper = new ProjectOpportunityMapper();
        Opportunity opportunity = new Opportunity();
        opportunity.setId(attachToExistingProjectDto.getOpportunityId());
        projectOpportunityMapper.setOpportunity(opportunity);
        Project project = new Project();

        project.setId(attachToExistingProjectDto.getProjectId());
        Project project1 = projectsRepository.findById(attachToExistingProjectDto.getProjectId()).orElse(new Project());
        String ttp="";
        System.out.println(attachToExistingProjectDto.getTargetTechnologyPlatform());

        if(attachToExistingProjectDto.getTargetTechnologyPlatform()!=null && !attachToExistingProjectDto.getTargetTechnologyPlatform().isEmpty() && !attachToExistingProjectDto.getTargetTechnologyPlatform().equals("")) {

            if (project1.getTargetTechnologyPlatform() != null && !project1.getTargetTechnologyPlatform().equals("") && !project1.getTargetTechnologyPlatform().isEmpty()) {
                ttp = project1.getTargetTechnologyPlatform() + ", " + attachToExistingProjectDto.getTargetTechnologyPlatform();
            } else {
                ttp.concat(attachToExistingProjectDto.getTargetTechnologyPlatform());
            }
        }else{
            if (project1.getTargetTechnologyPlatform() != null && !project1.getTargetTechnologyPlatform().equals("") && !project1.getTargetTechnologyPlatform().isEmpty()) {
                ttp = project1.getTargetTechnologyPlatform();
            } else {
                ttp = "";
            }
        }



        if (ttp.indexOf(",null") != -1) {
            ttp = ttp.replaceAll(",null", "");
        } else if (ttp.indexOf(", null") != -1) {
            ttp = ttp.replaceAll(", null", "");
        } else if (ttp.indexOf("null ,")!= -1) {
            ttp = ttp.replaceAll("null ,", "");
        } else if(ttp.indexOf("null")!= -1){
            ttp = ttp.replaceAll("null", "");
        }

        project1.setTargetTechnologyPlatform(ttp.trim());

        try{
            projectsRepository.save(project1);
        }catch(Exception ex){
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
        projectOpportunityMapper.setProject(project);
        try {
            projectOpportunityMapper = projectOpportunityMapperRepository.save(projectOpportunityMapper);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }

        createAndEditProjectService.updateAccessHistoryDetails(attachToExistingProjectDto.getProjectId(),
                projectOpportunityMapper.getLastModifiedByUser());
        updateHasProjectOfOpportunity(attachToExistingProjectDto.getOpportunityId());
        sendNotification(attachToExistingProjectDto);
    }

    /**
     * This method would be use for sending the notification for attaching Opportunity to Project.
     * @param attachToExistingProjectDto is using parameter AttachToExistingProjectDto.
     * @throws ActionFailureException exception would be throws, if Opportunity Data is not found.
     */
    @Transactional
    public void sendNotification(AttachToExistingProjectDto attachToExistingProjectDto) throws ActionFailureException {
        MailRequestDto mailRequestDto = new MailRequestDto();
        int projectId = attachToExistingProjectDto.getProjectId();
        int opportunityId = attachToExistingProjectDto.getOpportunityId();
        Opportunity opportunity = getOpportunityById(opportunityId);
        mailRequestDto.setOpportunityId(opportunity.getOpportunityId());
        Project project = createAndEditProjectService.getProjectById(projectId);
        mailRequestDto.setProjectName(project.getProjectName());
        mailRequestDto.setAccountName(project.getClient().getClientName());
        mailRequestDto.setGdpId(project.getGdpId());
        mailRequestDto.setLastModifiedBy(opportunity.getLastModifiedByUser());
        try {
            emailService.sendEmail(mailRequestDto, Constants.ADD_OPPORTUNITY, false,false);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_SEND_MAIL.name(), ex);
        }
    }

    /**
     * This method would be use for updating the Opportunity for the Project.
     * @param opportunityId is the id of Opportunity which needs to update.
     * @throws ActionFailureException exception would be throws, if Opportunity Data is not found.
     */
    @Transactional
    public void updateHasProjectOfOpportunity(int opportunityId) throws ActionFailureException {
        Opportunity opportunity = getOpportunityById(opportunityId);
        opportunity.setHasProject(true);
        try {
            opportunityRepository.save(opportunity);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This method would be use for getting the Opportunity using opportunityId.
     * @param opportunityId is the id of Opportunity.
     * @return this method would be return Opportunity information.
     * @throws ActionFailureException exception would be throws, if Opportunity Data is not found.
     */
    @Transactional
    public Opportunity getOpportunityById(int opportunityId) throws ActionFailureException {
        Optional<Opportunity> opportunityFound = opportunityRepository.findById(opportunityId);
        if (!opportunityFound.isPresent()) {
            throw new ActionFailureException(EStatusCode.RECORD_NOT_FOUND.name());
        }
        return opportunityFound.get();
    }
}
