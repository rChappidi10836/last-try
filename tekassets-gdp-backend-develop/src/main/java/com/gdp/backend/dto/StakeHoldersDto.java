package com.gdp.backend.dto;
import java.util.Date;

public class StakeHoldersDto {

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

    String PM;
    String GDM;
    String CSL;
    String DL;
    String BDM;
    String nationalAccountOwner;
    String OSGPOA;
    String OSGBOA;
    String salesRegion;
    String accountManager;
    String salesRD;
    String solutionArchitect;
    String practiceDirector;
    String practiceManager;
    String solutionExecutive;
    String transformationLead;
    String projectManager;
    String globalDeliveryDirector;
    String projectCoordinator;
    String trainingCoordinator;
//    String pillarLead;
//    String podLead;

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


    public StakeHoldersDto(String project, String accountName, String gdpId, String peopleSoftId, String peoplesoftEngagementId, String deliveryModel, String practiceEngagement, String locationOfServices, String opportunityId, String SMPLink, String businessUnit, String PM, String GDM, String CSL, String DL, String BDM, String nationalAccountOwner, String OSGPOA, String OSGBOA, String salesRegion, String accountManager, String salesRD, String solutionArchitect, String practiceDirector, String practiceManager, String solutionExecutive, String transformationLead, String projectManager, String globalDeliveryDirector, String projectCoordinator, String trainingCoordinator, String opportunityOwner, Date startDate, Date endDate, String projectStage, Date statusDate, String schedule, String CSAT, String budget, String projectRisk, String resources, String overallStatus, String riskProfileIndicator, Date riskSurveyDate,Date securityStatusDate, boolean active, Boolean dummyRecord, String scheduleComments, String CSATComments, String BudgetComments, String projectRiskComments, String resourcesComments, String summary) {
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
        this.PM=PM;
        this.GDM = GDM;
        this.CSL = CSL;
        this.DL = DL;
        this.BDM = BDM;
        this.nationalAccountOwner = nationalAccountOwner;
        this.OSGPOA = OSGPOA;
        this.OSGBOA = OSGBOA;
        this.salesRegion = salesRegion;
        this.accountManager = accountManager;
        this.salesRD = salesRD;
        this.solutionArchitect = solutionArchitect;
        this.practiceDirector = practiceDirector;
        this.practiceManager = practiceManager;
        this.solutionExecutive = solutionExecutive;
        this.transformationLead = transformationLead;
        this.projectManager = projectManager;
        this.globalDeliveryDirector = globalDeliveryDirector;
        this.projectCoordinator = projectCoordinator;
        this.trainingCoordinator = trainingCoordinator;
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
        this.riskSurveyDate=riskSurveyDate;
        this.securityStatusDate = securityStatusDate;
        this.active = active;
        this.dummyRecord = dummyRecord != null && dummyRecord;
        this.scheduleComments = scheduleComments;
        this.CSATComments = CSATComments;
        this.BudgetComments = BudgetComments;
        this.projectRiskComments = projectRiskComments;
        this.resourcesComments = resourcesComments;
        this.summary = summary;


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

    public String getPM() {
        return PM;
    }

    public void setPM(String PM) {
        this.PM = PM;
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

    public void setGDM(String GDM) {
        this.GDM = GDM;
    }

    public String getCSL() {
        return CSL;
    }

    public void setCSL(String CSL) {
        this.CSL = CSL;
    }

    public String getDL() {
        return DL;
    }

    public void setDL(String DL) {
        this.DL = DL;
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
        if (getDummyRecord()) {
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
        if (getDummyRecord()) {
            return "";
        }
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCSAT() {
        if (getDummyRecord()) {
            return "";
        }
        return CSAT;
    }

    public void setCSAT(String CSAT) {
        this.CSAT = CSAT;
    }

    public String getBudget() {
        if (getDummyRecord()) {
            return "";
        }
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getProjectRisk() {
        if (getDummyRecord()) {
            return "";
        }
        return projectRisk;
    }

    public void setProjectRisk(String projectRisk) {
        this.projectRisk = projectRisk;
    }

    public String getResources() {
        if (getDummyRecord()) {
            return "";
        }
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getOverallStatus() {
        if (getDummyRecord()) {
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

    public Date getRiskSurveyDate() {
        return riskSurveyDate;
    }

    public void setRiskSurveyDate(Date riskSurveyDate) {
        this.riskSurveyDate = riskSurveyDate;
    }

    public Date getSecurityStatusDate() {
        return securityStatusDate;
    }

    public void setSecurityStatusDate(Date securityStatusDate) {
        this.securityStatusDate = securityStatusDate;
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

    public String getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public String getSalesRD() {
        return salesRD;
    }

    public void setSalesRD(String salesRD) {
        this.salesRD = salesRD;
    }

    public String getSolutionArchitect() {
        return solutionArchitect;
    }

    public void setSolutionArchitect(String solutionArchitect) {
        this.solutionArchitect = solutionArchitect;
    }

    public String getPracticeDirector() {
        return practiceDirector;
    }

    public void setPracticeDirector(String practiceDirector) {
        this.practiceDirector = practiceDirector;
    }

    public String getPracticeManager() {
        return practiceManager;
    }

    public void setPracticeManager(String practiceManager) {
        this.practiceManager = practiceManager;
    }

    public String getSolutionExecutive() {
        return solutionExecutive;
    }

    public void setSolutionExecutive(String solutionExecutive) {
        this.solutionExecutive = solutionExecutive;
    }

    public String getTransformationLead() {
        return transformationLead;
    }

    public void setTransformationLead(String transformationLead) {
        this.transformationLead = transformationLead;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getGlobalDeliveryDirector() {
        return globalDeliveryDirector;
    }

    public void setGlobalDeliveryDirector(String globalDeliveryDirector) {
        this.globalDeliveryDirector = globalDeliveryDirector;
    }

    public String getProjectCoordinator() {
        return projectCoordinator;
    }

    public void setProjectCoordinator(String projectCoordinator) {
        this.projectCoordinator = projectCoordinator;
    }

    public String getTrainingCoordinator() {
        return trainingCoordinator;
    }

    public void setTrainingCoordinator(String trainingCoordinator) {
        this.trainingCoordinator = trainingCoordinator;
    }

//    public String getPillarLead() {
//        return pillarLead;
//    }
//
//    public void setPillarLead(String pillarLead) {
//        this.pillarLead = pillarLead;
//    }
//
//    public String getPodLead() {
//        return podLead;
//    }
//
//    public void setPodLead(String podLead) {
//        this.podLead = podLead;
//    }

    public static StakeHoldersDto stakeHoldersMapper(Object[] columns) {

        return new StakeHoldersDto(
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
                (String) columns[13],
                (String) columns[14],
                (String) columns[15],
                (String) columns[16],
                (String) columns[17],
                (String) columns[18],
                (String) columns[19],
                (String) columns[20],
                (String) columns[21],
                (String) columns[22],
                (String) columns[23],
                (String) columns[24],
                (String) columns[25],
                (String) columns[26],
                (String) columns[27],
                (String) columns[28],
                (String) columns[29],
                (String) columns[30],
                (String) columns[31],


                (Date) columns[32],
                (Date) columns[33],
                (String) columns[34],
                (Date) columns[35],
                (String) columns[36],
                (String) columns[37],
                (String) columns[38],
                (String) columns[39],
                (String) columns[40],
                (String) columns[41],
                (String) columns[42],
                (Date) columns[43],
                (Date) columns[44],
                (boolean) columns[45],
                (Boolean) columns[46],
                (String) columns[47],
                (String) columns[48],
                (String) columns[49],
                (String) columns[50],
                (String) columns[51],
                (String) columns[52]


        );
    }

}