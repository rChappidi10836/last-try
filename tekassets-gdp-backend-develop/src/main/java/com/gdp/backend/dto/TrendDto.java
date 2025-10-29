package com.gdp.backend.dto;

import java.util.Date;

public class TrendDto {


    private String practice;
    private String peopleSoftProjectId;
    private String peopleSoftEngagementId;
    private String GDPId;
    private String project;
    private String accountName;
    private String GDM;
    private String CSL;
    private String BDM;
    private String GDD;
    private String riskProfile;
    private String currentPhase;
    private String overAllStatus;
    private Date weekEndingDate;
    private String summary;

    public TrendDto(String practice, String peopleSoftProjectId, String peopleSoftEngagementId, String GDPId, String project, String accountName, String GDM, String CSL, String BDM, String GDD, String riskProfile, String currentPhase, String overAllStatus, Date weekEndingDate, String summary) {
        this.practice = practice;
        this.peopleSoftProjectId = peopleSoftProjectId;
        this.peopleSoftEngagementId = peopleSoftEngagementId;
        this.GDPId = GDPId;
        this.project = project;
        this.accountName = accountName;
        this.GDM = GDM;
        this.CSL = CSL;
        this.BDM = BDM;
        this.GDD = GDD;
        this.riskProfile = riskProfile;
        this.currentPhase = currentPhase;
        this.overAllStatus = overAllStatus;
        this.weekEndingDate = weekEndingDate;
        this.summary = summary;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getPeopleSoftProjectId() {
        return peopleSoftProjectId;
    }

    public void setPeopleSoftProjectId(String peopleSoftProjectId) {
        this.peopleSoftProjectId = peopleSoftProjectId;
    }

    public String getPeopleSoftEngagementId() {
        return peopleSoftEngagementId;
    }

    public void setPeopleSoftEngagementId(String peopleSoftEngagementId) {
        this.peopleSoftEngagementId = peopleSoftEngagementId;
    }

    public String getGDPId() {
        return GDPId;
    }

    public void setGDPId(String GDPId) {
        this.GDPId = GDPId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getGDM() {
        return GDM;
    }

    public void setGDM(String GDM) {
        this.GDM = GDM;
    }

    public String getCSL() {
        return CSL;
    }

    public void setCSL(String CSL) {
        this.CSL = CSL;
    }

    public String getBDM() {
        return BDM;
    }

    public void setBDM(String BDM) {
        this.BDM = BDM;
    }

    public String getGDD() { return GDD; }

    public void setGDD(String GDD) { this.GDD = GDD; }

    public String getRiskProfile() { return riskProfile; }

    public void setRiskProfile(String projectRisk) { this.riskProfile = riskProfile; }

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

    public static TrendDto trendMapper(Object[] columns) {

        return new TrendDto(
                (String) columns[0],
                (String) columns[1],
                (String) columns[2],
                (String) columns[3],
                (String) columns[4],
                (String) columns[5],
                (String) columns[6],
                (String) columns[7],
                (String) columns[8],
                (String) columns[9],
                (String) columns[10],
                (String) columns[11],
                (String) columns[12],
                (Date) columns[13],
                (String) columns[14]
        );
    }
}