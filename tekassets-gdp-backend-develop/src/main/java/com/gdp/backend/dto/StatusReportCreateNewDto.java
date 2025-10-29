package com.gdp.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StatusReportCreateNewDto {

    private Integer projectId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private String weekEndingDate;

    private String currentPhase;

    private String overAllStatus;

    private String statusSummary;

    private String scheduleStatus;

    private String scheduleComments;

    private String csatStatus;

    private String csatComments;

    private String budgetStatus;

    private String budgetComments;

    private String projectRiskStatus;

    private String projectRiskComments;

    private String resourcesStatus;

    private String resourcesComments;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(String weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getOverAllStatus() {
        return overAllStatus;
    }

    public void setOverAllStatus(String overAllStatus) {
        this.overAllStatus = overAllStatus;
    }

    public String getStatusSummary() {
        return statusSummary;
    }

    public void setStatusSummary(String statusSummary) {
        this.statusSummary =  statusSummary.replaceAll("[^\\p{L}\\p{N}\\p{P}\\s]", "");
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public String getScheduleComments() {
        return scheduleComments;
    }

    public void setScheduleComments(String scheduleComments) {
        this.scheduleComments = scheduleComments;
    }

    public String getCsatStatus() {
        return csatStatus;
    }

    public void setCsatStatus(String csatStatus) {
        this.csatStatus = csatStatus;
    }

    public String getCsatComments() {
        return csatComments;
    }

    public void setCsatComments(String csatComments) {
        this.csatComments = csatComments;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(String budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getBudgetComments() {
        return budgetComments;
    }

    public void setBudgetComments(String budgetComments) {
        this.budgetComments = budgetComments;
    }

    public String getProjectRiskStatus() {
        return projectRiskStatus;
    }

    public void setProjectRiskStatus(String projectRiskStatus) {
        this.projectRiskStatus = projectRiskStatus;
    }

    public String getProjectRiskComments() {
        return projectRiskComments;
    }

    public void setProjectRiskComments(String projectRiskComments) {
        this.projectRiskComments = projectRiskComments;
    }

    public String getResourcesStatus() {
        return resourcesStatus;
    }

    public void setResourcesStatus(String resourcesStatus) {
        this.resourcesStatus = resourcesStatus;
    }

    public String getResourcesComments() {
        return resourcesComments;
    }

    public void setResourcesComments(String resourcesComments) {
        this.resourcesComments = resourcesComments;
    }
}
