package com.gdp.backend.dto;

public class ProjectClientNameDto {

    private String projectName;

    private String accountName;

    public ProjectClientNameDto() {
    }

    public ProjectClientNameDto(String projectName, String accountName) {
        this.projectName = projectName;
        this.accountName = accountName;
    }

    public static ProjectClientNameDto mapToProjectClientNameDto(Object[] columns) {
        return new ProjectClientNameDto((String) columns[0], (String) columns[1]);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
