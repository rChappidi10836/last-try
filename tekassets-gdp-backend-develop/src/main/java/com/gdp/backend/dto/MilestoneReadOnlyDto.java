package com.gdp.backend.dto;

public class MilestoneReadOnlyDto {

    private Integer projectId;

    private String defaultMilestone;

    private DeliveryMilestoneDto deliveryMilestoneDto;

    private AgileSprintMilestoneDto agileSprintMilestoneDto;

    public String getDefaultMilestone() {
        return defaultMilestone;
    }

    public void setDefaultMilestone(String defaultMilestone) {
        this.defaultMilestone = defaultMilestone;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public DeliveryMilestoneDto getDeliveryMilestoneDto() {
        return deliveryMilestoneDto;
    }

    public void setDeliveryMilestoneDto(DeliveryMilestoneDto deliveryMilestoneDto) {
        this.deliveryMilestoneDto = deliveryMilestoneDto;
    }

    public AgileSprintMilestoneDto getAgileSprintMilestoneDto() {
        return agileSprintMilestoneDto;
    }

    public void setAgileSprintMilestoneDto(AgileSprintMilestoneDto agileSprintMilestoneDto) {
        this.agileSprintMilestoneDto = agileSprintMilestoneDto;
    }
}
