package com.gdp.backend.dto;

import java.util.Date;

public class MilestoneAgileSprintReportDto {
    String project;
    String accountName;
    String GDPId;

    String GDD;
    String track;
    Object sprint;
    Date startDate;
    Date finishDate;
    Object deliveredStories;
    Object committedStoryPoints;
    Object deliveredStoryPoints;
    Object addedStories;
    Object removedStories;
    Object capacity;
    Object estimate;
    String sprintStatus;
    Boolean status;
    String goalMet;

    public MilestoneAgileSprintReportDto(String project, String accountName,String GDD, String GDPId, String track, Object sprint, Date startDate, Date finishDate, Object deliveredStories, Object committedStoryPoints, Object deliveredStoryPoints, Object addedStories, Object removedStories, Object capacity, Object estimate, String sprintStatus, Boolean status, String goalMet) {
        this.project = project;
        this.accountName = accountName;
        this.GDD=GDD;
        this.GDPId = GDPId;
        this.track = track;
        this.sprint = sprint;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.deliveredStories = deliveredStories;
        this.committedStoryPoints = committedStoryPoints;
        this.deliveredStoryPoints = deliveredStoryPoints;
        this.addedStories = addedStories;
        this.removedStories = removedStories;
        this.capacity = capacity;
        this.estimate = estimate;
        this.sprintStatus = sprintStatus;
        this.status = status;
        this.goalMet = goalMet;
    }

    public String getProject() {
        return project;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getGDD(){return GDD;}
    public String getGDPId() {
        return GDPId;
    }

    public String getTrack() {
        return track;
    }

    public Object getSprint() {
        return sprint;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Object getDeliveredStories() {
        return deliveredStories;
    }

    public Object getCommittedStoryPoints() {
        return committedStoryPoints;
    }

    public Object getDeliveredStoryPoints() {
        return deliveredStoryPoints;
    }

    public Object getAddedStories() {
        return addedStories;
    }

    public Object getRemovedStories() {
        return removedStories;
    }

    public Object getCapacity() {
        return capacity;
    }

    public Object getEstimate() {
        return estimate;
    }

    public String getSprintStatus() {
        return sprintStatus;
    }

    public Boolean isStatus() {
        return status;
    }

    public String getGoalMet() {
        return goalMet;
    }

    public static MilestoneAgileSprintReportDto milestoneAgileSprintReportDtoMapper(Object[] columns) {
        return new MilestoneAgileSprintReportDto(
                (String) columns[0],
                (String) columns[1],
                (String) columns[2],
                (String) columns[3],
                (String) columns[4],
                columns[5],
                (Date) columns[6],
                (Date) columns[7],
                columns[8],
                columns[9],
                columns[10],
                columns[11],
                columns[12],
                columns[13],
                columns[14],
                (String) columns[15],
                (Boolean) columns[16],
                (String) columns[17]
        );
    }

}
