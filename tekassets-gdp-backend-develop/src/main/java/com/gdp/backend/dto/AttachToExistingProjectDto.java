package com.gdp.backend.dto;

public class AttachToExistingProjectDto {

    private Integer opportunityId;

    private Integer projectId;

    private String projectName;

    private Integer clientId;

    private String clientName;

    private String targetTechnologyPlatform;

    public String getTargetTechnologyPlatform() {
        return targetTechnologyPlatform;
    }

    public void setTargetTechnologyPlatform(String targetTechnologyPlatform) {
        this.targetTechnologyPlatform = targetTechnologyPlatform;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public AttachToExistingProjectDto() {
    }

    public AttachToExistingProjectDto(Integer projectId, String projectName, Integer clientId, String clientName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientId = clientId;
        this.clientName = clientName;
    }


    public static AttachToExistingProjectDto mapToAttachToExistingProjectDto(Object[] columns) {
        return new AttachToExistingProjectDto(
                (Integer) columns[1], (String) columns[2], (Integer) columns[0], (String) columns[3]
        );
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
