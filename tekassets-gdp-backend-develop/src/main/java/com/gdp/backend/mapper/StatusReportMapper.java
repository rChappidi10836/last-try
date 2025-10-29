package com.gdp.backend.mapper;

import com.gdp.backend.common.Utils;
import com.gdp.backend.dto.*;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.Project;
import com.gdp.backend.model.ProjectStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gdp.backend.common.Utils.getStatusForFrontend;

public class StatusReportMapper {

    private StatusReportMapper() throws ActionFailureException {
        throw new ActionFailureException(EStatusCode.ERROR_ON_INSTANTIATION.name());
    }

    public static StatusReportDto mapToStatusReportDto(Object[] columns, StatusReportDto statusReportDto) {
        statusReportDto.setLastUpdatedAt((Date) columns[17]);
        statusReportDto.setLastUpdatedBy((String) columns[16]);
        return statusReportDto;
    }

    public static StatusReportWeeklyStatusDto mapToStatusReportWeeklyStatusDto(Object[] columns) {
        StatusReportWeeklyStatusDto statusReportWeeklyStatusDto = new StatusReportWeeklyStatusDto();
        statusReportWeeklyStatusDto.setSecurityProfileStatus((String) columns[0]);
        statusReportWeeklyStatusDto.setDeliveryRiskIndicator((String) columns[1]);
        statusReportWeeklyStatusDto.setWeekEndingDate(OpportunityDto.getFormattedDate((Date) columns[2]));
        statusReportWeeklyStatusDto.setCurrentPhase((String) columns[3]);
        statusReportWeeklyStatusDto.setSchedule((String) columns[4]);
        statusReportWeeklyStatusDto.setCsat((String) columns[5]);
        statusReportWeeklyStatusDto.setBudget((String) columns[6]);
        statusReportWeeklyStatusDto.setProjectRisk((String) columns[7]);
        statusReportWeeklyStatusDto.setResources((String) columns[8]);
        statusReportWeeklyStatusDto.setOverAllStatus(getStatusForFrontend((String) columns[9]));
        statusReportWeeklyStatusDto.setSummary((String) columns[10]);
        statusReportWeeklyStatusDto.setScheduleComments((String) columns[11]);
        statusReportWeeklyStatusDto.setCsatComments((String) columns[12]);
        statusReportWeeklyStatusDto.setBudgetComments((String) columns[13]);
        statusReportWeeklyStatusDto.setProjectRiskComments((String) columns[14]);
        statusReportWeeklyStatusDto.setResourcesComments((String) columns[15]);
        return statusReportWeeklyStatusDto;
    }

    public static ProjectStatus mapStatusReportCreateNewDtoToProjectStatus(StatusReportCreateNewDto statusReportCreateNewDto
            , Date convertedWeekEndingDate, Boolean isCreateNew, ProjectStatus projectStatus) {
        if (Boolean.TRUE.equals(isCreateNew)) {
            Project project = new Project();
            project.setId(statusReportCreateNewDto.getProjectId());
            projectStatus.setProject(project);
            projectStatus.setActive(true);
            projectStatus.setWeekEndingDate(convertedWeekEndingDate);
        }
        projectStatus.setOverAllStatus(statusReportCreateNewDto.getOverAllStatus());
        projectStatus.setSummary(statusReportCreateNewDto.getStatusSummary());
        projectStatus.setCurrentPhase(statusReportCreateNewDto.getCurrentPhase());
        projectStatus.setSchedule(statusReportCreateNewDto.getScheduleStatus());
        projectStatus.setScheduleComments(statusReportCreateNewDto.getScheduleComments());
        projectStatus.setCsat(statusReportCreateNewDto.getCsatStatus());
        projectStatus.setCsatComments(statusReportCreateNewDto.getCsatComments());
        projectStatus.setBudget(statusReportCreateNewDto.getBudgetStatus());
        projectStatus.setBudgetComments(statusReportCreateNewDto.getBudgetComments());
        projectStatus.setProjectRisk(statusReportCreateNewDto.getProjectRiskStatus());
        projectStatus.setProjectRiskComments(statusReportCreateNewDto.getProjectRiskComments());
        projectStatus.setResources(statusReportCreateNewDto.getResourcesStatus());
        projectStatus.setResourcesComments(statusReportCreateNewDto.getResourcesComments());
        projectStatus.setDummyRecord(false);
        return projectStatus;
    }

    public static List<DeliveryHoursResponseDTO> mapToDeliveryHoursToResponseDTO(List<DeliveryHoursDto> deliveryHoursDtos) {
        List<DeliveryHoursResponseDTO> deliveryHoursResponseDTOS = new ArrayList<>();
        String wed = null;
        List<ResourceHoursDTO> resourceHours = null;
        DeliveryHoursResponseDTO deliveryHoursResponse = null;
        for (DeliveryHoursDto deliveryHoursDto : deliveryHoursDtos) {
            if (!Utils.getFormattedDate(deliveryHoursDto.getWeekEndingDate()).equals(wed)) {
                wed = Utils.getFormattedDate(deliveryHoursDto.getWeekEndingDate());
                resourceHours = new ArrayList<>();
                deliveryHoursResponse = new DeliveryHoursResponseDTO();
                deliveryHoursResponse.setWeekEndingDate(wed);
                deliveryHoursResponse.setResourceHours(resourceHours);
                deliveryHoursResponseDTOS.add(deliveryHoursResponse);
            }
            resourceHours.add(new ResourceHoursDTO(deliveryHoursDto.getId(), deliveryHoursDto.getName(), deliveryHoursDto.getHours() == null ? null : deliveryHoursDto.getHours().toString(), deliveryHoursDto.getResourceId()));
        }
        return deliveryHoursResponseDTOS;
    }

    public static void mapDeliveryLeadersToResponseDTO(Object[] o, List<ResourceHoursDTO> resourceHours) {
        Integer resourceId = (Integer) o[0];
        String name = Utils.getFullName((String) o[1], (String) o[2], (String) o[3]);
        String hours = "";
        try{
            hours = ((Integer) o[4]).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        ResourceHoursDTO resourceHour = new ResourceHoursDTO(null, name, hours, resourceId);
        resourceHours.add(resourceHour);
    }
}
