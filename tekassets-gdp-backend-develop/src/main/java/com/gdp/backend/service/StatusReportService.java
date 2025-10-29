package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.mapper.StatusReportMapper;
import com.gdp.backend.model.DeliveryHours;
import com.gdp.backend.model.ProjectStatus;
import com.gdp.backend.repository.DeliveryHoursRepository;
import com.gdp.backend.repository.ProjectStatusRepository;
import com.gdp.backend.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This Service class used for retrieving, saving, update and deleting the information related to the Status Report.
 * @author gdp
 *
 */
@Service
public class StatusReportService {

    @Autowired
    EntityManager entityManager;
    @Autowired
    ProjectStatusRepository projectStatusRepository;
    @Autowired
    SecurityProfileService securityProfileService;
    @Autowired
    RiskSurveyService riskSurveyService;
    @Autowired
    DeliveryHoursRepository deliveryHoursRepository;
    @Autowired
    private CreateProjectManuallyService createProjectManuallyService;
    @Autowired
    private ProjectsRepository projectsRepository;

    /**
     * This Service would be use for getting Project status report related information using projectId.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @param projectId is the id of existing Project.
     * @return this service would be return status Report data.
     * @throws ActionFailureException exception would be throws if Project status data not found.
     */
    public StatusReportDto getStatusReportByProjectId(int pageNumber, int pageSize, int projectId) throws ActionFailureException {
        StatusReportDto statusReportDto = new StatusReportDto();
        Query query = entityManager.createNativeQuery(Queries.STATUS_REPORT_BY_PROJECT_ID);
        query.setParameter(1, projectId);
        statusReportDto.setTotalCount(query.getResultStream().count());
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> statusReportList;
        try {
            statusReportList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<StatusReportWeeklyStatusDto> statusReportWeeklyStatusDtoList = new ArrayList<>(statusReportList.size());
        ProjectClientNameDto projectClientNameDto = riskSurveyService.getProjectAndClientNameByProjectId(projectId);
        statusReportDto.setProjectName(projectClientNameDto.getProjectName());
        statusReportDto.setAccountName(projectClientNameDto.getAccountName());
        statusReportDto.setSecurityProfileStatus(securityProfileService.getSecurityProfileStatusByProjectId(projectId));
        statusReportDto.setDeliveryRiskIndicator(securityProfileService.getRiskIndicatorByProjectId(projectId));
        for (Object[] row : statusReportList) {
            StatusReportWeeklyStatusDto statusReportWeeklyStatusDto = StatusReportMapper.mapToStatusReportWeeklyStatusDto(row);
            statusReportWeeklyStatusDtoList.add(statusReportWeeklyStatusDto);
        }
        statusReportDto.setWeeklyStatus(statusReportWeeklyStatusDtoList);
        if (!CollectionUtils.isEmpty(statusReportWeeklyStatusDtoList)) {
            statusReportDto = StatusReportMapper.mapToStatusReportDto(statusReportList.get(0), statusReportDto);
        }
        return statusReportDto;
    }

    /**
     * This method would be use for converting String to Date format.
     * @param date is the Week Ending Date. 
     * @return this method would be return formated Date.
     * @throws ActionFailureException exception would be throws if date is not formated.
     */
    public Date convertStringToDate(String date) throws ActionFailureException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //input date format coming from frontend
        try {
            return format.parse(date);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_DATE_PARSING.name(), ex);
        }
    }

    /**
     * This Service would be use for updating the Project status report related data.
     * @param statusReportCreateNewDto is the data which needs to update.
     * @return this service would be return projectStatus.
     * @throws ActionFailureException exception would be throws if Project status data not found.
     */
    @Transactional
    public ProjectStatus updateStatusReport(StatusReportCreateNewDto statusReportCreateNewDto) throws ActionFailureException {
        Date convertedWeekEndingDate = convertStringToDate(statusReportCreateNewDto.getWeekEndingDate());
        ProjectStatus projectStatus = getProjectStatusByWeekEndingDateAndProjectId(statusReportCreateNewDto.getProjectId(), convertedWeekEndingDate);
        if (isEmpty(projectStatus)) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name());
        }
        projectStatus = StatusReportMapper.mapStatusReportCreateNewDtoToProjectStatus(statusReportCreateNewDto, convertedWeekEndingDate, false, projectStatus);
        projectStatus = setRiskIndicatorAndSecurityProfileStatus(projectStatus, statusReportCreateNewDto);
        projectStatus = saveProjectStatus(projectStatus);
        if (projectStatus.getCurrentPhase().equals(Constants.CLOSED)) {
            setProjectInActive(statusReportCreateNewDto.getProjectId());
        } else {
            // in case a project is closed and then a new report it submitted this will re-activate the project.
            setProjectActive(statusReportCreateNewDto.getProjectId());
        }
        return projectStatus;
    }

    /**
     * This Service would be use for creating the Project new status report data.
     * @param statusReportCreateNewDto is the data which needs to create.
     * @return this method would be return projectStatus.
     * @throws ActionFailureException exception would be throws if Project status data not saved.
     */
    @Transactional
    public ProjectStatus createNewStatusReport(StatusReportCreateNewDto statusReportCreateNewDto) throws ActionFailureException {
        Date convertedWeekEndingDate = convertStringToDate(statusReportCreateNewDto.getWeekEndingDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertedWeekEndingDate);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            // date is ahead by one day i.e. sunday, subtract one day to make it saturday
            calendar.add(Calendar.DATE, -1);
            convertedWeekEndingDate = calendar.getTime();
        }
        ProjectStatus projectStatus = getProjectStatusByWeekEndingDateAndProjectId(statusReportCreateNewDto.getProjectId(), convertedWeekEndingDate);
        if (!isEmpty(projectStatus)) {
            throw new ActionFailureException(EStatusCode.DUPLICATE_WEEK_ENDING_DATE.name());
        }
        projectStatus = createNewStatusReport(statusReportCreateNewDto, convertedWeekEndingDate);
        projectStatus = saveProjectStatus(projectStatus);
        if (projectStatus.getCurrentPhase().equals(Constants.CLOSED)) {
            setProjectInActive(statusReportCreateNewDto.getProjectId());
        } else {
            // in case a project is closed and then a new report it submitted this will re-activate the project.
            setProjectActive(statusReportCreateNewDto.getProjectId());
        }
        return projectStatus;
    }

    /**
     * This method would be use for getting the Project Status data using Week Ending Date and ProjectId.
     * @param projectId is the id of existing Project.
     * @param weekEndingDate is the Project Week Ending Date.
     * @return this method would be return Project Status data.
     */
    public ProjectStatus getProjectStatusByWeekEndingDateAndProjectId(int projectId, Date weekEndingDate) {
        Optional<ProjectStatus> optionalProjectStatus = projectStatusRepository.findByProject_IdAndWeekEndingDateAndIsActive(projectId, weekEndingDate, true);
        if (!optionalProjectStatus.isPresent()) {
            return null;
        }
        return optionalProjectStatus.get();
    }

    /**
     * This method would be use for set the Risk indicator and SecurityProfile Status.
     * @param projectStatus is the data which need to set for project status.
     * @param statusReportCreateNewDto is the data which needs to create project status.
     * @return this method would be return projectStatus.
     */
    public ProjectStatus setRiskIndicatorAndSecurityProfileStatus(ProjectStatus projectStatus, StatusReportCreateNewDto statusReportCreateNewDto) {
        projectStatus.setDeliveryRiskIndicator(securityProfileService.getRiskIndicatorByProjectId(statusReportCreateNewDto.getProjectId()));
        projectStatus.setSecurityProfileStatus(securityProfileService.getSecurityProfileStatusByProjectId(statusReportCreateNewDto.getProjectId()));
        return projectStatus;
    }

    /**
     * This method would be use for creating the Project new status report data.
     * @param statusReportCreateNewDto is the data which needs to create.
     * @param convertedWeekEndingDate is the date which need to format.
     * @return this method would be return projectStatus data.
     */
    public ProjectStatus createNewStatusReport(StatusReportCreateNewDto statusReportCreateNewDto, Date convertedWeekEndingDate) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus = StatusReportMapper.mapStatusReportCreateNewDtoToProjectStatus(statusReportCreateNewDto, convertedWeekEndingDate, true, projectStatus);
        setRiskIndicatorAndSecurityProfileStatus(projectStatus, statusReportCreateNewDto);
        return projectStatus;
    }

    /**
     * This method would be use for saving the Project status data.
     * @param projectStatus is the data which needs to save.
     * @return this method would be return ProjectStatus data.
     * @throws ActionFailureException exception would be throws if Project status data not saved.
     */
    public ProjectStatus saveProjectStatus(ProjectStatus projectStatus) throws ActionFailureException {
        try {
            return projectStatusRepository.save(projectStatus);
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
        }
    }

    /**
     * This Service would be use for deleting the Project status report related data.
     * @param statusReportCreateNewDto is the data which needs to delete.
     * @return this method would be return ProjectStatus.
     * @throws ActionFailureException exception would be throws if Project status data not deleted.
     */
    @Transactional
    public ProjectStatus deleteStatusReport(StatusReportCreateNewDto statusReportCreateNewDto) throws ActionFailureException {
        Date convertedWeekEndingDate = convertStringToDate(statusReportCreateNewDto.getWeekEndingDate());
        ProjectStatus projectStatus = getProjectStatusByWeekEndingDateAndProjectId(statusReportCreateNewDto.getProjectId(), convertedWeekEndingDate);
        if (isEmpty(projectStatus)) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name());
        }
        projectStatus.setActive(false);
        cleanMilestonesAndDeliveryHours(statusReportCreateNewDto.getProjectId(), statusReportCreateNewDto.getWeekEndingDate());
        return saveProjectStatus(projectStatus);
    }

    /**
     * This method would be use for cleaning the Milestones and DeliveryHours.
     * @param projectId is the id of existing Project.
     * @param weekEndingDate is the Milestones week Ending Date.
     */
    private void cleanMilestonesAndDeliveryHours(int projectId, String weekEndingDate) {
        Query query = entityManager.createNativeQuery(Queries.ON_STATUS_REPORT_DELETE_DELIVERY_MILESTONES);
        query.setParameter(1, projectId);
        query.setParameter(2, weekEndingDate);
        query.executeUpdate();
        query = entityManager.createNativeQuery(Queries.ON_STATUS_REPORT_DELETE_AGILE_MILESTONES);
        query.setParameter(1, projectId);
        query.setParameter(2, weekEndingDate);
        query.executeUpdate();
        query = entityManager.createNativeQuery(Queries.ON_STATUS_REPORT_DELETE_DELIVERY_HOURS);
        query.setParameter(1, projectId);
        query.setParameter(2, weekEndingDate);
        query.executeUpdate();
    }

    /**
     * This method would be use for getting the Project Delivery Hours related data using projectId.
     * @param projectId is the id of existing Project.
     * @return this method would be return list of delivery Hours data.
     * @throws ActionFailureException exception would be throws if Project Delivery Hours data not found.
     */
    public List<DeliveryHoursResponseDTO> getDeliveryHoursByProjectId(int projectId) throws ActionFailureException {
        CreateProjectFormDto projectById = createProjectManuallyService.findProjectById(projectId);
        if (Constants.FLEXIBLE_CAPACITY.equalsIgnoreCase(projectById.getDeliveryOrganizationName()) || projectById.getIsFlex()) {
            Query query = entityManager.createNativeQuery(Queries.DELIVERY_HOURS_BY_PROJECT_ID, "DeliveryHoursMapping");
            query.setParameter(1, projectId);
            List<DeliveryHoursDto> statusReportList;
            try {
                statusReportList = query.getResultList();
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
            }
            List<DeliveryHoursResponseDTO> deliveryHoursResponse = StatusReportMapper.mapToDeliveryHoursToResponseDTO(statusReportList);

            List<Object[]> resultList;
            try {
                resultList = entityManager.createNativeQuery(Queries.DELIVERY_LEADERS_FOR_FLEX_IT_BY_PROJECT_ID).setParameter(1, projectId).getResultList();
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
            }
            List<ResourceHoursDTO> deliveryLeaders = new ArrayList<>();
            for (Object[] o : resultList) {
                StatusReportMapper.mapDeliveryLeadersToResponseDTO(o, deliveryLeaders);
            }
            deliveryHoursResponse.add(new DeliveryHoursResponseDTO(null, null, deliveryLeaders));
            return deliveryHoursResponse;
        } else {
            return null;
        }
    }

    /**
     * This Service would be use for getting the Project Delivery Hours related data using projectId and week ending Date.
     * @param projectId is the id of existing Project.
     * @param weekEndingDate is the Project Delivery Hours week ending Date.
     * @return this method would be return list of deliveryHours data.
     * @throws ActionFailureException exception would be throws if Project Delivery Hours WED data not found.
     */
    public List<DeliveryHoursDto> getDeliveryHoursByProjectIdWED(int projectId, String weekEndingDate) throws ActionFailureException {
        List<DeliveryHoursDto> deliveryHoursDtos = new ArrayList<>();
        CreateProjectFormDto projectById = createProjectManuallyService.findProjectById(projectId);
        if (Constants.FLEXIBLE_CAPACITY.equalsIgnoreCase(projectById.getDeliveryOrganizationName()) || projectById.getIsFlex()) {
            Query query = entityManager.createNativeQuery(Queries.DELIVERY_HOURS_BY_PROJECT_ID_WED, "DeliveryHoursMapping");
            query.setParameter(1, projectId);
            query.setParameter(2, weekEndingDate);
            try {
                deliveryHoursDtos.addAll(query.getResultList());
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
            }
        }
        if (deliveryHoursDtos.size() > 0) {
            for (DeliveryHoursDto deliveryHoursDto : deliveryHoursDtos) {
                if (deliveryHoursDto.getHours() == null) {
                    // try pulling delivery hours from past reports.
                    deliveryHoursDto.setHours(
                            deliveryHoursRepository.findLastDeliveryHourForResource(projectId, deliveryHoursDto.getResourceId())
                    );
                }
            }
        }
        return deliveryHoursDtos;
    }

    /**
     * This service would be use for saving the Project Delivery Hours related data.
     * @param deliveryHoursRequest is the data which needs to save.
     * @throws ActionFailureException exception would be throws if Project Delivery Hours related data not save.
     */
    public void saveDeliveryHours(DeliveryHoursRequestDTO deliveryHoursRequest) throws ActionFailureException {
        for (ResourceHoursDTO resourceHours : deliveryHoursRequest.getResourceHours()) {
            DeliveryHours deliveryHour = new DeliveryHours();
            deliveryHour.setId(resourceHours.getId());
            deliveryHour.setHours(StringUtils.isEmpty(resourceHours.getHours()) ? null : Integer.valueOf(resourceHours.getHours()));
            deliveryHour.setProjectId(deliveryHoursRequest.getProjectId());
            deliveryHour.setResourceId(resourceHours.getResourceId());
            deliveryHour.setWeekEndingDate(deliveryHoursRequest.getWeekEndingDate());

            try {
                deliveryHoursRepository.save(deliveryHour);
            } catch (Exception ex) {
                throw new ActionFailureException(EStatusCode.ERROR_ON_ADD.name(), ex);
            }
        }
    }

    /**
     * This method would be use for mapping the Project as Active or InActive status using projectId.
     * @param projectId is the id of existing Project.
     */
    @Transactional
    public void setProjectInActive(int projectId) {
        projectsRepository.setProjectInActive(projectId);
    }

    @Transactional
    public void setProjectActive(int projectId){
        projectsRepository.setProjectActive(projectId);
    }

}
