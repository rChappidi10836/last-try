package com.gdp.backend.dto;

import java.util.List;

public class MilestoneAddDto {
    private Integer projectId;
    private List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneDto;
    private List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestoneDto;
    private String weekEndingDate;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<DeliveryMilestoneReadOnlyDto> getDeliveryMilestoneDto() {
        return deliveryMilestoneDto;
    }

    public void setDeliveryMilestoneDto(List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneDto) {
        this.deliveryMilestoneDto = deliveryMilestoneDto;
    }

    public List<AgileSprintMilestoneReadOnlyDto> getAgileSprintMilestoneDto() {
        return agileSprintMilestoneDto;
    }

    public void setAgileSprintMilestoneDto(List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestoneDto) {
        this.agileSprintMilestoneDto = agileSprintMilestoneDto;
    }

    public String getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(String weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }
}
