package com.gdp.backend.dto;

import java.util.Date;

public class MilestoneDeliveryReportDto {

    String project;
    String accountName;

    String GDD;
    String GDPId;
    String deliveryType;
    String milestoneDescription;
    Date startDate;
    Date finishDate;
    String comments;
    String Status;

    public MilestoneDeliveryReportDto(String project, String accountName,String GDD, String GDPId, String deliveryType, String milestoneDescription, Date startDate, Date finishDate, String comments, String status) {
        this.project = project;
        this.accountName = accountName;
        this.GDD=GDD;
        this.GDPId = GDPId;
        this.deliveryType = deliveryType;
        this.milestoneDescription = milestoneDescription;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.comments = comments;
        Status = status;
    }

    public String getProject() {
        return project;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getGDD() {
        return GDD;
    }
    public String getGDPId() {
        return GDPId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public String getMilestoneDescription() {
        return milestoneDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getComments() {
        return comments;
    }

    public String isStatus() {
        return Status;
    }


    public static MilestoneDeliveryReportDto milestoneDeliveryReportDtoMapper(Object[] columns) {
        return new MilestoneDeliveryReportDto(
                (String) columns[0],
                (String) columns[1],
                (String) columns[2],
                (String) columns[3],
                (String) columns[4],
                (String) columns[5],
                (Date) columns[6],
                (Date) columns[7],
                (String) columns[8],
                (String) columns[9]
        );
    }

}
