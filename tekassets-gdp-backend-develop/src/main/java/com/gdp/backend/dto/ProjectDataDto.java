package com.gdp.backend.dto;

import java.util.Date;

public class ProjectDataDto {
    String project;
    String accountName;
    String GdpId;
    String peopleSoftId;
    String peoplesoftEngagementId;
    String deliveryModel;
    String practiceEngagement;
    String locationOfServices;
    String opportunityId;
    String SMPLink;
    String businessUnit;

    String serviceType;
    String GDM;
    String PM;
    String GDD;
    String CSL;
    String BDM;
    String nationalAccountOwner;
    String OSGPOA;
    String OSGBOA;
    String salesRegion;
    String opportunityOwner;
    Date startDate;
    Date endDate;
    String projectStage;
    Date statusDate;
    String schedule;
    String CSAT;
    String Budget;
    String projectRisk;
    String resources;
    String overallStatus;
    String riskProfileIndicator;
    Date securityStatusDate;
    Date riskSurveyDate;
    boolean active;
    Boolean dummyRecord;
    String scheduleComments;
    String CSATComments;
    String BudgetComments;
    String projectRiskComments;
    String resourcesComments;
    String summary;
    String TargetTechnologyPlatform;

    public ProjectDataDto(String project, String accountName, String gdpId, String peopleSoftId, String peoplesoftEngagementId, String deliveryModel, String practiceEngagement, String locationOfServices, String opportunityId, String SMPLink, String businessUnit, String serviceType, String GDM,String PM, String GDD, String CSL, String BDM, String nationalAccountOwner, String OSGPOA, String OSGBOA, String salesRegion, String opportunityOwner, Date startDate, Date endDate, String projectStage, Date statusDate, String schedule, String CSAT, String budget, String projectRisk, String resources, String overallStatus, String riskProfileIndicator,Date riskSurveyDate, Date securityStatusDate, boolean active, Boolean dummyRecord, String scheduleComments,String CSATComments,String BudgetComments,String projectRiskComments,String resourcesComments, String summary,String TargetTechnologyPlatform) {
        this.project = project;
        this.accountName = accountName;
        GdpId = gdpId;
        this.peopleSoftId = peopleSoftId;
        this.peoplesoftEngagementId = peoplesoftEngagementId;
        this.deliveryModel = deliveryModel;
        this.practiceEngagement = practiceEngagement;
        this.locationOfServices = locationOfServices;
        this.opportunityId = opportunityId;
        this.SMPLink = SMPLink;
        this.businessUnit = businessUnit;
        this.serviceType=serviceType;
        this.GDM = GDM;
        this.PM=PM;
        this.GDD = GDD;
        this.CSL = CSL;
        this.BDM = BDM;
        this.nationalAccountOwner = nationalAccountOwner;
        this.OSGPOA = OSGPOA;
        this.OSGBOA = OSGBOA;
        this.salesRegion = salesRegion;
        this.opportunityOwner = opportunityOwner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectStage = projectStage;
        this.statusDate = statusDate;
        this.schedule = schedule;
        this.CSAT = CSAT;
        Budget = budget;
        this.projectRisk = projectRisk;
        this.resources = resources;
        this.overallStatus = overallStatus;
        this.riskProfileIndicator = riskProfileIndicator;
        this.riskSurveyDate= riskSurveyDate;
        this.securityStatusDate = securityStatusDate;
        this.active = active;
        this.dummyRecord = dummyRecord != null && dummyRecord;
        this.scheduleComments = scheduleComments;
        this.CSATComments = CSATComments;
        this.BudgetComments = BudgetComments;
        this.projectRiskComments = projectRiskComments;
        this.resourcesComments = resourcesComments;
        this.summary = summary;
        this.TargetTechnologyPlatform =TargetTechnologyPlatform;
    }

    public String getTargetTechnologyPlatform() {
        return TargetTechnologyPlatform;
    }

    public void setTargetTechnologyPlatform(String targetTechnologyPlatform) {
        TargetTechnologyPlatform = targetTechnologyPlatform;
    }

    public String getSummary() {
        return summary;
    }

    public String getScheduleComments() {
        return scheduleComments;
    }

    public String getCSATComments() {
        return CSATComments;
    }

    public String getBudgetComments() {
        return BudgetComments;
    }

    public String getProjectRiskComments() {
        return projectRiskComments;
    }

    public String getResourcesComments() {
        return resourcesComments;
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

    public String getGdpId() {
        return GdpId;
    }

    public void setGdpId(String gdpId) {
        GdpId = gdpId;
    }

    public String getPeopleSoftId() {
        return peopleSoftId;
    }

    public void setPeopleSoftId(String peopleSoftId) {
        this.peopleSoftId = peopleSoftId;
    }

    public String getPeoplesoftEngagementId() {
        return peoplesoftEngagementId;
    }

    public void setPeoplesoftEngagementId(String peoplesoftEngagementId) {
        this.peoplesoftEngagementId = peoplesoftEngagementId;
    }

    public String getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(String deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    public String getPracticeEngagement() {
        return practiceEngagement;
    }

    public void setPracticeEngagement(String practiceEngagement) {
        this.practiceEngagement = practiceEngagement;
    }

    public String getLocationOfServices() {
        return locationOfServices;
    }

    public void setLocationOfServices(String locationOfServices) {
        this.locationOfServices = locationOfServices;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getSMPLink() {
        return SMPLink;
    }

    public void setSMPLink(String SMPLink) {
        this.SMPLink = SMPLink;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getGDM() {
        return GDM;
    }

    public String getPM() {
        return PM;
    }
    public void setGDM(String GDM) {
        this.GDM = GDM;
    }

    public String getGDD() { return GDD; }

    public void setGDD(String GDM) { this.GDD = GDD; }

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

    public String getNationalAccountOwner() {
        return nationalAccountOwner;
    }

    public void setNationalAccountOwner(String nationalAccountOwner) {
        this.nationalAccountOwner = nationalAccountOwner;
    }

    public String getOSGPOA() {
        return OSGPOA;
    }

    public void setOSGPOA(String OSGPOA) {
        this.OSGPOA = OSGPOA;
    }

    public String getOSGBOA() {
        return OSGBOA;
    }

    public void setOSGBOA(String OSGBOA) {
        this.OSGBOA = OSGBOA;
    }

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

    public String getOpportunityOwner() {
        return opportunityOwner;
    }

    public void setOpportunityOwner(String opportunityOwner) {
        this.opportunityOwner = opportunityOwner;
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

    public String getProjectStage() {
        if(getDummyRecord()){
            return "";
        }
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getSchedule() {
        if(getDummyRecord()){
            return "";
        }
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCSAT() {
        if(getDummyRecord()){
            return "";
        }
        return CSAT;
    }

    public void setCSAT(String CSAT) {
        this.CSAT = CSAT;
    }

    public String getBudget() {
        if(getDummyRecord()){
            return "";
        }
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getProjectRisk() {
        if(getDummyRecord()){
            return "";
        }
        return projectRisk;
    }

    public void setProjectRisk(String projectRisk) {
        this.projectRisk = projectRisk;
    }

    public String getResources() {
        if(getDummyRecord()){
            return "";
        }
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getOverallStatus() {
        if(getDummyRecord()){
            return "";
        }
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public String getRiskProfileIndicator() {
        return riskProfileIndicator;
    }

    public void setRiskProfileIndicator(String riskProfileIndicator) {
        this.riskProfileIndicator = riskProfileIndicator;
    }

    public Date getSecurityStatusDate() {
        return securityStatusDate;
    }

    public void setSecurityStatusDate(Date securityStatusDate) {
        this.securityStatusDate = securityStatusDate;
    }

    public Date getRiskSurveyDate() {
        return riskSurveyDate;
    }

    public void setRiskSurveyDate(Date riskSurveyDate) {
        this.riskSurveyDate = riskSurveyDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Boolean getDummyRecord() {
        return dummyRecord;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public static ProjectDataDto projectDataMapper(Object[] columns){
        return new ProjectDataDto(
                (String) columns[0],
                (String)columns[1],
                (String)columns[2],
                (String)columns[3],
                (String)columns[4],
                (String)columns[5],
                (String)columns[6],
                (String)columns[7],
                (String)columns[8],
                (String)columns[9],
                (String)columns[10],
                (String)columns[11],
                (String)columns[12],
                (String)columns[13],
                (String)columns[14],
                (String)columns[15],
                (String)columns[16],
                (String)columns[17],
                (String)columns[18],
                (String)columns[19],
                (String)columns[20],
                (String)columns[21],
                (Date)columns[22],
                (Date)columns[23],
                (String)columns[24],
                (Date)columns[25],
                (String)columns[26],
                (String)columns[27],
                (String)columns[28],
                (String)columns[29],
                (String)columns[30],
                (String)columns[31],
                (String)columns[32],
                (Date)columns[33],
                (Date)columns[34],
                (Boolean) columns[35],
                (Boolean) columns[36],
                (String)columns[37],
                (String)columns[38],
                (String)columns[39],
                (String)columns[40],
                (String)columns[41],
                (String)columns[42],
                (String)columns[43]
        );
    }

    @Override
    public String toString() {
        return "ProjectDataDto{" +
                "project='" + project + '\'' +
                ", accountName='" + accountName + '\'' +
                ", GdpId='" + GdpId + '\'' +
                ", peopleSoftId='" + peopleSoftId + '\'' +
                ", peoplesoftEngagementId='" + peoplesoftEngagementId + '\'' +
                ", deliveryModel='" + deliveryModel + '\'' +
                ", practiceEngagement='" + practiceEngagement + '\'' +
                ", locationOfServices='" + locationOfServices + '\'' +
                ", opportunityId='" + opportunityId + '\'' +
                ", SMPLink='" + SMPLink + '\'' +
                ", businessUnit='" + businessUnit + '\'' +
                ", sserviceType='" + serviceType + '\''+
                ", GDM='" + GDM + '\'' +
                ", PM='" + PM + '\'' +
                ", CSL='" + CSL + '\'' +
                ", BDM='" + BDM + '\'' +
                ", nationalAccountOwner='" + nationalAccountOwner + '\'' +
                ", OSGPOA='" + OSGPOA + '\'' +
                ", OSGBOA='" + OSGBOA + '\'' +
                ", salesRegion='" + salesRegion + '\'' +
                ", opportunityOwner='" + opportunityOwner + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", projectStage='" + projectStage + '\'' +
                ", statusDate=" + statusDate +
                ", schedule='" + schedule + '\'' +
                ", CSAT='" + CSAT + '\'' +
                ", Budget='" + Budget + '\'' +
                ", projectRisk='" + projectRisk + '\'' +
                ", resources='" + resources + '\'' +
                ", overallStatus='" + overallStatus + '\'' +
                ", riskProfileIndicator='" + riskProfileIndicator + '\'' +
                ", securityStatusDate=" + securityStatusDate +
                ", riskSurveyDate=" + riskSurveyDate +
                ", active=" + active +
                ", dummyRecord=" + dummyRecord +
                ", scheduleComments='" + scheduleComments + '\'' +
                ", CSATComments='" + CSATComments + '\'' +
                ", BudgetComments='" + BudgetComments + '\'' +
                ", projectRiskComments='" + projectRiskComments + '\'' +
                ", resourcesComments='" + resourcesComments + '\'' +
                ", summary='" + summary + '\'' +
                ", TargetTechnologyPlatform='" + TargetTechnologyPlatform + '\''+
                '}';
    }
}
