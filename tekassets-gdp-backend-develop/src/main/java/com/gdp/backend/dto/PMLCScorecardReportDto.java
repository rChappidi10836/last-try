package com.gdp.backend.dto;

import java.util.Date;

public class PMLCScorecardReportDto {
    String accountName;
    String projectName;
    String riskProfile;
    String SMPLink;
    String GDM;
    String CSL;
    String GDD;
    String PM;
    String Phase;
    Date startDate;
    Date endDate;
    Date weekEndingDate;
    String schedule;
    String CSAT;
    String Budget;
    String projectRisk;
    String resources;
    String overallStatus;
    String deliveryModel;
    String practiceEngagement;
    String GDPId;
    Boolean dummyRecord;

    public PMLCScorecardReportDto(String accountName, String projectName, String riskProfile, String SMPLink, String GDM, String CSL, String GDD, String PM, String phase, Date startDate, Date endDate, Date weekEndingDate, String schedule, String CSAT, String budget, String projectRisk, String resources, String overallStatus, String deliveryModel, String practiceEngagement, String GDPId, Boolean dummyRecord) {
        this.accountName = accountName;
        this.projectName = projectName;
        this.riskProfile = riskProfile;
        this.SMPLink = SMPLink;
        this.GDM = GDM;
        this.CSL = CSL;
        this.GDD = GDD;
        this.PM = PM;
        Phase = phase;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekEndingDate = weekEndingDate;
        this.schedule = schedule;
        this.CSAT = CSAT;
        Budget = budget;
        this.projectRisk = projectRisk;
        this.resources = resources;
        this.overallStatus = overallStatus;
        this.deliveryModel = deliveryModel;
        this.practiceEngagement = practiceEngagement;
        this.GDPId = GDPId;
        this.dummyRecord = dummyRecord != null && dummyRecord;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public String getSMPLink() {
        return SMPLink;
    }

    public String getGDM() {
        return GDM;
    }

    public String getCSL() {
        return CSL;
    }

    public String getGDD() { return GDD; }

    public String getPM() {
        return PM;
    }

    public String getPhase() {
        if(getDummyRecord()){
            return "";
        }
        return Phase;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getSchedule() {
        if(getDummyRecord()){
            return "";
        }
        return schedule;
    }

    public String getCSAT() {
        if(getDummyRecord()){
            return "";
        }
        return CSAT;
    }

    public String getBudget() {
        if(getDummyRecord()){
            return "";
        }
        return Budget;
    }

    public String getProjectRisk() {
        if(getDummyRecord()){
            return "";
        }
        return projectRisk;
    }

    public String getResources() {
        if(getDummyRecord()){
            return "";
        }
        return resources;
    }

    public String getOverallStatus() {
        if(getDummyRecord()){
            return "";
        }
        return overallStatus;
    }

    public String getDeliveryModel() {
        return deliveryModel;
    }

    public String getPracticeEngagement() {
        return practiceEngagement;
    }

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public String getGDPId() {
        return GDPId;
    }

    public Boolean getDummyRecord() {
        return dummyRecord;
    }

    public static PMLCScorecardReportDto scorecardReportDtoMapper(Object[] columns) {
        return new PMLCScorecardReportDto(
                (String) columns[0],
                (String) columns[1],
                (String) columns[2],
                (String) columns[3],
                (String) columns[4],
                (String) columns[5],
                (String) columns[6],
                (String) columns[7],
                (String) columns[8],
                (Date) columns[9],
                (Date) columns[10],
                (Date) columns[11],
                (String) columns[12],
                (String) columns[13],
                (String) columns[14],
                (String) columns[15],
                (String) columns[16],
                (String) columns[17],
                (String) columns[18],
                (String) columns[19],
                (String) columns[20],
                (Boolean) columns[21]
        );
    }
}
