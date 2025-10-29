package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AgileSprintMilestones extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    @JsonBackReference
    private Project project;

    @Column(name = "WeekEndingDate")
    private Date weekEndingDate;

    @Column(name = "Track")
    private String track;

    @Column(name = "Sprint")
    private Integer sprint;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "DeliveredStories")
    private Integer deliveredStories;

    @Column(name = "CommittedStoryPoints")
    private Integer committedStoryPoints;

    @Column(name = "DeliveredStoryPoints")
    private Integer deliveredStoryPoints;

    @Column(name = "AddedStories")
    private Integer addedStories;

    @Column(name = "RemovedStories")
    private Integer removedStories;

    @Column(name = "Capacity")
    private Integer capacity;

    @Column(name = "Estimate")
    private Integer estimate;

    @Column(name = "SprintStatus")
    private String sprintStatus;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "GoalMet")
    private String goalMet;

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public Integer getSprint() {
        return sprint;
    }

    public void setSprint(Integer sprint) {
        this.sprint = sprint;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDeliveredStories() {
        return deliveredStories;
    }

    public void setDeliveredStories(Integer deliveredStories) {
        this.deliveredStories = deliveredStories;
    }

    public Integer getCommittedStoryPoints() {
        return committedStoryPoints;
    }

    public void setCommittedStoryPoints(Integer committedStoryPoints) {
        this.committedStoryPoints = committedStoryPoints;
    }

    public Integer getDeliveredStoryPoints() {
        return deliveredStoryPoints;
    }

    public void setDeliveredStoryPoints(Integer deliveredStoryPoints) {
        this.deliveredStoryPoints = deliveredStoryPoints;
    }

    public Integer getAddedStories() {
        return addedStories;
    }

    public void setAddedStories(Integer addedStories) {
        this.addedStories = addedStories;
    }

    public Integer getRemovedStories() {
        return removedStories;
    }

    public void setRemovedStories(Integer removedStories) {
        this.removedStories = removedStories;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public String getSprintStatus() {
        return sprintStatus;
    }

    public void setSprintStatus(String sprintStatus) {
        this.sprintStatus = sprintStatus;
    }

    public String getGoalMet() {
        return goalMet;
    }

    public void setGoalMet(String goalMet) {
        this.goalMet = goalMet;
    }
}
