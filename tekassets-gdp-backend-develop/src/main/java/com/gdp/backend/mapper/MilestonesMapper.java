package com.gdp.backend.mapper;

import com.gdp.backend.dto.AgileSprintMilestoneReadOnlyDto;
import com.gdp.backend.dto.DeliveryMilestoneReadOnlyDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.AgileSprintMilestones;
import com.gdp.backend.model.DeliveryMilestoneType;
import com.gdp.backend.model.DeliveryMilestones;
import com.gdp.backend.model.Project;

import java.util.Date;

import static com.gdp.backend.common.Utils.*;
import static java.lang.Integer.parseInt;

public class MilestonesMapper {

    private MilestonesMapper() throws ActionFailureException {
        throw new ActionFailureException(EStatusCode.ERROR_ON_INSTANTIATION.name());
    }

    public static DeliveryMilestoneReadOnlyDto mapToDeliveryMilestoneReadOnlyDto(Object[] columns) {
        DeliveryMilestoneReadOnlyDto deliveryMilestoneReadOnlyDto = new DeliveryMilestoneReadOnlyDto();
        deliveryMilestoneReadOnlyDto.setDescription((String) columns[0]);
        deliveryMilestoneReadOnlyDto.setType((String) columns[1]);
        deliveryMilestoneReadOnlyDto.setStartDate((Date) columns[2]);
        deliveryMilestoneReadOnlyDto.setEndDate((Date) columns[3]);
        deliveryMilestoneReadOnlyDto.setStatus((String) columns[4]);
        deliveryMilestoneReadOnlyDto.setComments((String) columns[5]);
        deliveryMilestoneReadOnlyDto.setLastUpdatedBy((String) columns[6]);
        deliveryMilestoneReadOnlyDto.setLastUpdatedAt(getLastUpdatedAtFormatted((Date) columns[7]));
        deliveryMilestoneReadOnlyDto.setWeekEndingDate(getFormattedDate((Date) columns[8]));
        deliveryMilestoneReadOnlyDto.setId((Integer) columns[9]);
        deliveryMilestoneReadOnlyDto.setActive((Boolean) columns[10]);
        if(columns[11]!=null)
        deliveryMilestoneReadOnlyDto.setTypeId((Integer)columns[11]);
        return deliveryMilestoneReadOnlyDto;
    }

    public static AgileSprintMilestoneReadOnlyDto mapToAgileSprintMilestoneReadOnlyDto(Object[] columns) {
        AgileSprintMilestoneReadOnlyDto agileSprintMilestoneReadOnlyDto = new AgileSprintMilestoneReadOnlyDto();
        agileSprintMilestoneReadOnlyDto.setTrack((String) columns[0]);
        agileSprintMilestoneReadOnlyDto.setSprint(columns[1] == null ? null : ((Integer) columns[1]).toString());
        agileSprintMilestoneReadOnlyDto.setStartDate((Date) columns[2]);
        agileSprintMilestoneReadOnlyDto.setEndDate((Date) columns[3]);
        agileSprintMilestoneReadOnlyDto.setDeliveredStories(columns[4] == null ? null : ((Integer) columns[4]).toString());
        agileSprintMilestoneReadOnlyDto.setCommittedStoryPoints(columns[5] == null ? null : ((Integer) columns[5]).toString());
        agileSprintMilestoneReadOnlyDto.setDeliveredStoryPoints(columns[6] == null ? null : ((Integer) columns[6]).toString());
        agileSprintMilestoneReadOnlyDto.setAddedStories(columns[7] == null ? null : ((Integer) columns[7]).toString());
        agileSprintMilestoneReadOnlyDto.setRemovedStories(columns[8] == null ? null : ((Integer) columns[8]).toString());
        agileSprintMilestoneReadOnlyDto.setCapacity(columns[9] == null ? null : ((Integer) columns[9]).toString());
        agileSprintMilestoneReadOnlyDto.setEstimate(columns[10] == null ? null : ((Integer) columns[10]).toString());
        agileSprintMilestoneReadOnlyDto.setSprintStatus((String) columns[11]);
        agileSprintMilestoneReadOnlyDto.setLastUpdatedBy((String) columns[12]);
        agileSprintMilestoneReadOnlyDto.setLastUpdatedAt(getLastUpdatedAtFormatted((Date) columns[13]));
        agileSprintMilestoneReadOnlyDto.setWeekEndingDate(getFormattedDate((Date) columns[14]));
        agileSprintMilestoneReadOnlyDto.setId((Integer) columns[15]);
        agileSprintMilestoneReadOnlyDto.setActive((Boolean) columns[16]);
        agileSprintMilestoneReadOnlyDto.setGoalMet((String)columns[17]);
        return agileSprintMilestoneReadOnlyDto;
    }

    public static DeliveryMilestoneReadOnlyDto getDeliveryMilestoneDtoFromEntity(DeliveryMilestones deliveryMilestones) {
        DeliveryMilestoneReadOnlyDto deliveryMilestoneReadOnlyDto = new DeliveryMilestoneReadOnlyDto();

        deliveryMilestoneReadOnlyDto.setId(deliveryMilestones.getId());
        deliveryMilestoneReadOnlyDto.setDescription(deliveryMilestones.getDescription());
        deliveryMilestoneReadOnlyDto.setStartDate(deliveryMilestones.getStartDate());
        deliveryMilestoneReadOnlyDto.setEndDate(deliveryMilestones.getEndDate());
        deliveryMilestoneReadOnlyDto.setType(deliveryMilestones.getType());
        deliveryMilestoneReadOnlyDto.setStatus(deliveryMilestones.getStatus());
        deliveryMilestoneReadOnlyDto.setComments(deliveryMilestones.getComments());
        deliveryMilestoneReadOnlyDto.setLastUpdatedAt(getLastUpdatedAtFormatted(deliveryMilestones.getModifiedTime()));
        deliveryMilestoneReadOnlyDto.setLastUpdatedBy(deliveryMilestones.getLastModifiedByUser());

        return deliveryMilestoneReadOnlyDto;
    }

    public static AgileSprintMilestoneReadOnlyDto getAgileSprintMilestoneDtoFromEntity(AgileSprintMilestones agileSprintMilestones) {
        AgileSprintMilestoneReadOnlyDto agileSprintMilestoneReadOnlyDto = new AgileSprintMilestoneReadOnlyDto();

        agileSprintMilestoneReadOnlyDto.setId(agileSprintMilestones.getId());
        agileSprintMilestoneReadOnlyDto.setAddedStories(agileSprintMilestones.getAddedStories() == null ? null : agileSprintMilestones.getAddedStories().toString());
        agileSprintMilestoneReadOnlyDto.setCapacity(agileSprintMilestones.getCapacity() == null ? null : agileSprintMilestones.getCapacity().toString());
        agileSprintMilestoneReadOnlyDto.setCommittedStoryPoints(agileSprintMilestones.getCommittedStoryPoints() == null ? null : agileSprintMilestones.getCommittedStoryPoints().toString());
        agileSprintMilestoneReadOnlyDto.setDeliveredStories(agileSprintMilestones.getDeliveredStories() == null ? null : agileSprintMilestones.getDeliveredStories().toString());
        agileSprintMilestoneReadOnlyDto.setDeliveredStoryPoints(agileSprintMilestones.getDeliveredStoryPoints() == null ? null : agileSprintMilestones.getDeliveredStoryPoints().toString());
        agileSprintMilestoneReadOnlyDto.setEndDate(agileSprintMilestones.getEndDate());
        agileSprintMilestoneReadOnlyDto.setEstimate(agileSprintMilestones.getEstimate() == null ? null : agileSprintMilestones.getEstimate().toString());
        agileSprintMilestoneReadOnlyDto.setLastUpdatedAt(getLastUpdatedAtFormatted(agileSprintMilestones.getModifiedTime()));
        agileSprintMilestoneReadOnlyDto.setLastUpdatedBy(agileSprintMilestones.getLastModifiedByUser());
        agileSprintMilestoneReadOnlyDto.setRemovedStories(agileSprintMilestones.getRemovedStories() == null ? null : agileSprintMilestones.getRemovedStories().toString());
        agileSprintMilestoneReadOnlyDto.setSprint(agileSprintMilestones.getSprint() == null ? null : agileSprintMilestones.getSprint().toString());
        agileSprintMilestoneReadOnlyDto.setSprintStatus(agileSprintMilestones.getSprintStatus());
        agileSprintMilestoneReadOnlyDto.setStartDate(agileSprintMilestones.getStartDate());
        agileSprintMilestoneReadOnlyDto.setTrack(agileSprintMilestones.getTrack());
        agileSprintMilestoneReadOnlyDto.setWeekEndingDate(getFormattedDate(agileSprintMilestones.getWeekEndingDate()));
        agileSprintMilestoneReadOnlyDto.setGoalMet(agileSprintMilestones.getGoalMet());

        return agileSprintMilestoneReadOnlyDto;
    }

    public static DeliveryMilestones updateDeliveryMilestones(DeliveryMilestoneReadOnlyDto deliveryMilestoneReadOnlyDto, Project project, DeliveryMilestoneType deliveryMilestoneType, String reportWeekEndingDate) {
        DeliveryMilestones deliveryMilestones = new DeliveryMilestones();

        deliveryMilestones.setId(deliveryMilestoneReadOnlyDto.getId());
        deliveryMilestones.setProject(project);
        String wed = deliveryMilestoneReadOnlyDto.getWeekEndingDate();
        if(wed==null)
            wed = reportWeekEndingDate;
        deliveryMilestones.setWeekEndingDate(getDate(wed));
        deliveryMilestones.setStartDate(deliveryMilestoneReadOnlyDto.getStartDate());
        deliveryMilestones.setEndDate(deliveryMilestoneReadOnlyDto.getEndDate());
        deliveryMilestones.setDescription(deliveryMilestoneReadOnlyDto.getDescription());
        deliveryMilestones.setType(deliveryMilestoneReadOnlyDto.getType());
        deliveryMilestones.setStatus(deliveryMilestoneReadOnlyDto.getStatus());
        deliveryMilestones.setComments(deliveryMilestoneReadOnlyDto.getComments());
        deliveryMilestones.setActive(deliveryMilestoneReadOnlyDto.getId() == null ? true : deliveryMilestoneReadOnlyDto.getActive());
        deliveryMilestones.setDeliveryMilestoneType(deliveryMilestoneType);
        return deliveryMilestones;
    }

    public static AgileSprintMilestones updateAgileSprintMilestones(AgileSprintMilestoneReadOnlyDto agileSprintMilestoneReadOnlyDto, Project project, String reportWeekEndingDate) {
        AgileSprintMilestones agileSprintMilestones = new AgileSprintMilestones();

        agileSprintMilestones.setId(agileSprintMilestoneReadOnlyDto.getId());
        agileSprintMilestones.setProject(project);
        String wed = agileSprintMilestoneReadOnlyDto.getWeekEndingDate(); //week ending date
        if(wed==null) wed = reportWeekEndingDate; //in create copy week ending date will come null so use report week ending date.
        agileSprintMilestones.setWeekEndingDate(getDate(wed));
        agileSprintMilestones.setStartDate(agileSprintMilestoneReadOnlyDto.getStartDate());
        agileSprintMilestones.setEndDate(agileSprintMilestoneReadOnlyDto.getEndDate());
        agileSprintMilestones.setTrack(agileSprintMilestoneReadOnlyDto.getTrack());
        agileSprintMilestones.setSprint(agileSprintMilestoneReadOnlyDto.getSprint() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getSprint()));
        agileSprintMilestones.setDeliveredStories(agileSprintMilestoneReadOnlyDto.getDeliveredStories() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getDeliveredStories()));
        agileSprintMilestones.setDeliveredStoryPoints(agileSprintMilestoneReadOnlyDto.getDeliveredStoryPoints() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getDeliveredStoryPoints()));
        agileSprintMilestones.setCommittedStoryPoints(agileSprintMilestoneReadOnlyDto.getCommittedStoryPoints() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getCommittedStoryPoints()));
        agileSprintMilestones.setAddedStories(agileSprintMilestoneReadOnlyDto.getAddedStories() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getAddedStories()));
        agileSprintMilestones.setRemovedStories(agileSprintMilestoneReadOnlyDto.getRemovedStories() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getRemovedStories()));
        agileSprintMilestones.setCapacity(agileSprintMilestoneReadOnlyDto.getCapacity() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getCapacity()));
        agileSprintMilestones.setEstimate(agileSprintMilestoneReadOnlyDto.getEstimate() == null ? null : parseInt(agileSprintMilestoneReadOnlyDto.getEstimate()));
        agileSprintMilestones.setSprintStatus(agileSprintMilestoneReadOnlyDto.getSprintStatus());
        agileSprintMilestones.setActive(agileSprintMilestoneReadOnlyDto.getId() == null ? true : agileSprintMilestoneReadOnlyDto.getActive());
        agileSprintMilestones.setGoalMet(agileSprintMilestoneReadOnlyDto.getGoalMet());

        return agileSprintMilestones;
    }
}
