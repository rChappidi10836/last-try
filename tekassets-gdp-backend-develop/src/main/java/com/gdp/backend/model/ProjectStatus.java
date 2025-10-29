package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table
public class ProjectStatus extends Base {

    @Column(name = "WeekEndingDate")
    private Date weekEndingDate;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "Active")
    private Boolean isActive;

    @Column(name = "DummyRecord")
    private Boolean dummyRecord;

    @Column(name = "OverAllStatus")
    private String overAllStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    @JsonBackReference
    private Project project;

    @Column(name = "SecurityProfileStatus")
    private String securityProfileStatus;

    @Column(name = "DeliveryRiskIndicator")
    private String deliveryRiskIndicator;

    @Column(name = "CurrentPhase")
    private String currentPhase;

    @Column(name = "Schedule")
    private String schedule;

    @Column(name = "ScheduleComments")
    private String scheduleComments;

    @Column(name = "CSAT")
    private String csat;

    @Column(name = "CSATComments")
    private String csatComments;

    @Column(name = "Budget")
    private String budget;

    @Column(name = "BudgetComments")
    private String budgetComments;

    @Column(name = "ProjectRisk")
    private String projectRisk;

    @Column(name = "ProjectRiskComments")
    private String projectRiskComments;

    @Column(name = "Resources")
    private String resources;

    @Column(name = "ResourcesComments")
    private String resourcesComments;

    public Boolean getDummyRecord() {
        return dummyRecord;
    }

    public void setDummyRecord(Boolean dummyRecord) {
        this.dummyRecord = dummyRecord;
    }

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getOverAllStatus() {
        return overAllStatus;
    }

    public void setOverAllStatus(String overAllStatus) {
        this.overAllStatus = overAllStatus;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSecurityProfileStatus() {
        return securityProfileStatus;
    }

    public void setSecurityProfileStatus(String securityProfileStatus) {
        this.securityProfileStatus = securityProfileStatus;
    }

    public String getDeliveryRiskIndicator() {
        return deliveryRiskIndicator;
    }

    public void setDeliveryRiskIndicator(String deliveryRiskIndicator) {
        this.deliveryRiskIndicator = deliveryRiskIndicator;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getScheduleComments() {
        return scheduleComments;
    }

    public void setScheduleComments(String scheduleComments) {
        this.scheduleComments = scheduleComments;
    }

    public String getCsat() {
        return csat;
    }

    public void setCsat(String csat) {
        this.csat = csat;
    }

    public String getCsatComments() {
        return csatComments;
    }

    public void setCsatComments(String csatComments) {
        this.csatComments = csatComments;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getBudgetComments() {
        return budgetComments;
    }

    public void setBudgetComments(String budgetComments) {
        this.budgetComments = budgetComments;
    }

    public String getProjectRisk() {
        return projectRisk;
    }

    public void setProjectRisk(String projectRisk) {
        this.projectRisk = projectRisk;
    }

    public String getProjectRiskComments() {
        return projectRiskComments;
    }

    public void setProjectRiskComments(String projectRiskComments) {
        this.projectRiskComments = projectRiskComments;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getResourcesComments() {
        return resourcesComments;
    }

    public void setResourcesComments(String resourcesComments) {
        this.resourcesComments = resourcesComments;
    }
}
