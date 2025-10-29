package com.gdp.backend.dto;

public class AddResourceDto {

    private Integer resourceId;

    private Integer resourceRoleId;

    private String resourceRoleName;

    private String resourceName;

    private Boolean isOtherTeamMember;

    public AddResourceDto() {
    }

    public AddResourceDto(Object[] columns, String resourceName) {
        this.resourceId = (Integer) columns[0];
        this.resourceRoleId = (Integer) columns[4];
        this.resourceRoleName = (String) columns[5];
        this.isOtherTeamMember = (Boolean)columns[6];
        this.resourceName = resourceName;
    }

    public String getResourceRoleName() {
        return resourceRoleName;
    }

    public void setResourceRoleName(String resourceRoleName) {
        this.resourceRoleName = resourceRoleName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getResourceRoleId() {
        return resourceRoleId;
    }

    public void setResourceRoleId(Integer resourceRoleId) {
        this.resourceRoleId = resourceRoleId;
    }

    public Boolean getOtherTeamMember() {
        return isOtherTeamMember;
    }

    public void setOtherTeamMember(Boolean otherTeamMember) {
        isOtherTeamMember = otherTeamMember;
    }
}
