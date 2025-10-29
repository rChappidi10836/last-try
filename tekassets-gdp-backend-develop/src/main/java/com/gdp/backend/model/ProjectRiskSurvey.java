package com.gdp.backend.model;

import javax.persistence.*;

@Entity
public class ProjectRiskSurvey extends Base {

    @Column(name = "IsWarrantyApplicable")
    private String isWarrantyApplicable;

    @Column(name = "LiabilityLimits")
    private String liabilityLimits;

    @Column(name = "ContractType")
    private String contractType;

    @Column(name = "CustomerCreditScore")
    private String customerCreditScore;

    @Column(name = "SensitiveData")
    private String sensitiveData;

    @Column(name = "IsComplianceAssociated")
    private String isComplianceAssociated;

    @Column(name = "HardwareAndEquipment")
    private String hardwareAndEquipment;

    @Column(name = "IsCovered")
    private String isCovered;

    @Column(name = "Location")
    private String location;

    @Column(name = "TypeOfService")
    private String typeOfService;

    @Column(name = "ContractualDuration")
    private String contractualDuration;

    @Column(name = "IsEstablishedAccount")
    private String isEstablishedAccount;

    @Column(name = "TotalProjectedRevenue")
    private String totalProjectedRevenue;

    @Column(name = "EngagementWithTek")
    private String engagementWithTek;

    @Column(name = "Is3rdPartyDependent")
    private String is3rdPartyDependent;

    @Column(name = "HasUniqueChallenges")
    private String hasUniqueChallenges;

    @Column(name = "HighRiskCategory")
    private String hasHighRiskCategory;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "RiskIndicator")
    private String riskIndicator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;

    public String getIsWarrantyApplicable() {
        return isWarrantyApplicable;
    }

    public void setIsWarrantyApplicable(String isWarrantyApplicable) {
        this.isWarrantyApplicable = isWarrantyApplicable;
    }

    public String getRiskIndicator() {
        return riskIndicator;
    }

    public void setRiskIndicator(String riskIndicator) {
        this.riskIndicator = riskIndicator;
    }

    public String getLiabilityLimits() {
        return liabilityLimits;
    }

    public void setLiabilityLimits(String liabilityLimits) {
        this.liabilityLimits = liabilityLimits;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCustomerCreditScore() {
        return customerCreditScore;
    }

    public void setCustomerCreditScore(String customerCreditScore) {
        this.customerCreditScore = customerCreditScore;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    public String getIsComplianceAssociated() {
        return isComplianceAssociated;
    }

    public void setIsComplianceAssociated(String isComplianceAssociated) {
        this.isComplianceAssociated = isComplianceAssociated;
    }

    public String getHardwareAndEquipment() {
        return hardwareAndEquipment;
    }

    public void setHardwareAndEquipment(String hardwareAndEquipment) {
        this.hardwareAndEquipment = hardwareAndEquipment;
    }

    public String getIsCovered() {
        return isCovered;
    }

    public void setIsCovered(String isCovered) {
        this.isCovered = isCovered;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getContractualDuration() {
        return contractualDuration;
    }

    public void setContractualDuration(String contractualDuration) {
        this.contractualDuration = contractualDuration;
    }

    public String getIsEstablishedAccount() {
        return isEstablishedAccount;
    }

    public void setIsEstablishedAccount(String isEstablishedAccount) {
        this.isEstablishedAccount = isEstablishedAccount;
    }

    public String getTotalProjectedRevenue() {
        return totalProjectedRevenue;
    }

    public void setTotalProjectedRevenue(String totalProjectedRevenue) {
        this.totalProjectedRevenue = totalProjectedRevenue;
    }

    public String getEngagementWithTek() {
        return engagementWithTek;
    }

    public void setEngagementWithTek(String engagementWithTek) {
        this.engagementWithTek = engagementWithTek;
    }

    public String getIs3rdPartyDependent() {
        return is3rdPartyDependent;
    }

    public void setIs3rdPartyDependent(String is3rdPartyDependent) {
        this.is3rdPartyDependent = is3rdPartyDependent;
    }

    public String getHasUniqueChallenges() {
        return hasUniqueChallenges;
    }

    public void setHasUniqueChallenges(String hasUniqueChallenges) {
        this.hasUniqueChallenges = hasUniqueChallenges;
    }

    public String getHasHighRiskCategory() {
        return hasHighRiskCategory;
    }

    public void setHasHighRiskCategory(String hasHighRiskCategory) {
        this.hasHighRiskCategory = hasHighRiskCategory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
