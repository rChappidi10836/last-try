package com.gdp.backend.dto;

import com.gdp.backend.common.Utils;

import java.util.Date;
import java.util.Objects;

public class AgileSprintMilestoneReadOnlyDto {

    private Integer id;

    private String track;

    private String sprint;

    private Date startDate;

    private Date endDate;

    private String deliveredStories;

    private String committedStoryPoints;

    private String deliveredStoryPoints;

    private String addedStories;

    private String removedStories;

    private String capacity;

    private String estimate;

    private String sprintStatus;

    private String lastUpdatedBy;

    private String lastUpdatedAt;

    private String weekEndingDate;

    private String goalMet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgileSprintMilestoneReadOnlyDto)) return false;
        AgileSprintMilestoneReadOnlyDto that = (AgileSprintMilestoneReadOnlyDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(track, that.track) &&
                Objects.equals(sprint, that.sprint) &&
                Objects.equals(Utils.getFormattedDate(startDate), Utils.getFormattedDate(that.startDate)) &&
                Objects.equals(Utils.getFormattedDate(endDate), Utils.getFormattedDate(that.endDate)) &&
                Objects.equals(deliveredStories, that.deliveredStories) &&
                Objects.equals(committedStoryPoints, that.committedStoryPoints) &&
                Objects.equals(deliveredStoryPoints, that.deliveredStoryPoints) &&
                Objects.equals(addedStories, that.addedStories) &&
                Objects.equals(removedStories, that.removedStories) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(estimate, that.estimate) &&
                sprintStatus.equals(that.sprintStatus) &&
                isActive.equals(that.isActive) &&
                Objects.equals(goalMet, that.goalMet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, track, sprint, startDate, endDate, deliveredStories, committedStoryPoints, deliveredStoryPoints, addedStories, removedStories, capacity, estimate, sprintStatus, isActive, goalMet);
    }

    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(String weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public String getDeliveredStories() {
        return deliveredStories;
    }

    public void setDeliveredStories(String deliveredStories) {
        this.deliveredStories = deliveredStories;
    }

    public String getCommittedStoryPoints() {
        return committedStoryPoints;
    }

    public void setCommittedStoryPoints(String committedStoryPoints) {
        this.committedStoryPoints = committedStoryPoints;
    }

    public String getDeliveredStoryPoints() {
        return deliveredStoryPoints;
    }

    public void setDeliveredStoryPoints(String deliveredStoryPoints) {
        this.deliveredStoryPoints = deliveredStoryPoints;
    }

    public String getAddedStories() {
        return addedStories;
    }

    public void setAddedStories(String addedStories) {
        this.addedStories = addedStories;
    }

    public String getRemovedStories() {
        return removedStories;
    }

    public void setRemovedStories(String removedStories) {
        this.removedStories = removedStories;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getSprintStatus() {
        return sprintStatus;
    }

    public void setSprintStatus(String sprintStatus) {
        this.sprintStatus = sprintStatus;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getGoalMet() {
        return goalMet;
    }

    public void setGoalMet(String goalMet) {
        this.goalMet = goalMet;
    }
}
