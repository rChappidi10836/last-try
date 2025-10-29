package com.gdp.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "GDP_Archived_Practice_Details")
public class GDPArchivedPracticeDetails {

    @Id
    @Column(name = "Project_ID")
    private String projectId;

    @Column(name = "Project_Name")
    private String projectName;

    @Column(name = "Client_Name")
    private String clientName;

    @Column(name = "GDP_ID")
    private String gdpId;

    @Column(name = "Opportunity_ID")
    private String opportunityId;

    @Column(name = "Business_Unit")
    private String businessUnit;

    @Column(name = "Delivery_Model")
    private String deliveryModel;

    @Column(name = "Delivery_Organization")
    private String deliveryOrganization;

    @Column(name = "Sales_Region")
    private String salesRegion;

    @Column(name = "Practice_Engagement")
    private String practiceEngagement;

    @Column(name = "Delivery_Lead")
    private String deliveryLead;

    @Column(name = "Global_Delivery_Manager")
    private String globalDeliveryManager;

    @Column(name = "Customer_Success_Leader")
    private String customerSuccessLeader;

    @Column(name = "Project_Manager")
    private String projectManager;

    @Column(name = "Secondary_DMORPM")
    private String secondaryDMORPM;

    @Column(name = "Practice_Lead")
    private String practiceLead;

    @Column(name = "Practice_Manager")
    private String practiceManager;

    @Column(name = "BDMORAM")
    private String bdmORAM;

    @Column(name = "Solution_Engineer")
    private String solutionEngineer;

    @Column(name = "National_Account_Owner")
    private String nationalAccountOwner;

    @Column(name = "Project_Phase")
    private String projectPhase;

    @Column(name = "Start_Date")
    private Date startDate;

    @Column(name = "End_Date")
    private Date endDate;

    @Column(name = "SMP_Site")
    private String smpSite;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getGdpId() {
        return gdpId;
    }

    public void setGdpId(String gdpId) {
        this.gdpId = gdpId;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(String deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    public String getDeliveryOrganization() {
        return deliveryOrganization;
    }

    public void setDeliveryOrganization(String deliveryOrganization) {
        this.deliveryOrganization = deliveryOrganization;
    }

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

    public String getPracticeEngagement() {
        return practiceEngagement;
    }

    public void setPracticeEngagement(String practiceEngagement) {
        this.practiceEngagement = practiceEngagement;
    }

    public String getDeliveryLead() {
        return deliveryLead;
    }

    public void setDeliveryLead(String deliveryLead) {
        this.deliveryLead = deliveryLead;
    }

    public String getGlobalDeliveryManager() {
        return globalDeliveryManager;
    }

    public void setGlobalDeliveryManager(String globalDeliveryManager) {
        this.globalDeliveryManager = globalDeliveryManager;
    }

    public String getCustomerSuccessLeader() {
        return customerSuccessLeader;
    }

    public void setCustomerSuccessLeader(String customerSuccessLeader) {
        this.customerSuccessLeader = customerSuccessLeader;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getSecondaryDMORPM() {
        return secondaryDMORPM;
    }

    public void setSecondaryDMORPM(String secondaryDMORPM) {
        this.secondaryDMORPM = secondaryDMORPM;
    }

    public String getPracticeLead() {
        return practiceLead;
    }

    public void setPracticeLead(String practiceLead) {
        this.practiceLead = practiceLead;
    }

    public String getPracticeManager() {
        return practiceManager;
    }

    public void setPracticeManager(String practiceManager) {
        this.practiceManager = practiceManager;
    }

    public String getBdmORAM() {
        return bdmORAM;
    }

    public void setBdmORAM(String bdmORAM) {
        this.bdmORAM = bdmORAM;
    }

    public String getSolutionEngineer() {
        return solutionEngineer;
    }

    public void setSolutionEngineer(String solutionEngineer) {
        this.solutionEngineer = solutionEngineer;
    }

    public String getNationalAccountOwner() {
        return nationalAccountOwner;
    }

    public void setNationalAccountOwner(String nationalAccountOwner) {
        this.nationalAccountOwner = nationalAccountOwner;
    }

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
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

    public String getSmpSite() {
        return smpSite;
    }

    public void setSmpSite(String smpSite) {
        this.smpSite = smpSite;
    }
}
