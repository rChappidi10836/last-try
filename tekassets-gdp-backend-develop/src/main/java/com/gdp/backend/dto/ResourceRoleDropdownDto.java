package com.gdp.backend.dto;

public class ResourceRoleDropdownDto {

    private Integer resourceRoleId;

    private String resourceRoleName;

    private String engagementType;

    public ResourceRoleDropdownDto() {
    }

    public ResourceRoleDropdownDto(Integer resourceRoleId, String resourceRoleName, String engagementType) {
        this.resourceRoleId = resourceRoleId;
        this.resourceRoleName = resourceRoleName;
        this.engagementType = engagementType;
    }

    public String getEngagementType() {
        return engagementType;
    }

    public void setEngagementType(String engagementType) {
        this.engagementType = engagementType;
    }

    public Integer getResourceRoleId() {
        return resourceRoleId;
    }

    public void setResourceRoleId(Integer resourceRoleId) {
        this.resourceRoleId = resourceRoleId;
    }

    public String getResourceRoleName() {
        return resourceRoleName;
    }

    public void setResourceRoleName(String resourceRoleName) {
        this.resourceRoleName = resourceRoleName;
    }
}
