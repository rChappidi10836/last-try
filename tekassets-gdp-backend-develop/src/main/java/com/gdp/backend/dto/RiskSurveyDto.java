package com.gdp.backend.dto;

public class RiskSurveyDto {

    private Integer projectId;

    private String projectName;

    private String accountName;

    private String indicatorColor;

    private String isWarrantyApplicable;

    private String liabilityLimits;

    private String contractType;

    private String customerCreditScore;

    private String sensitiveData;

    private String isComplianceAssociated;

    private String hardwareAndEquipment;

    private String isCovered;

    private String location;

    private String typeOfService;

    private String contractualDuration;

    private String isEstablishedAccount;

    private String totalProjectedRevenue;

    private String engagementWithTek;

    private String is3rdPartyDependent;

    private String hasUniqueChallenges;

    private String hasHighRiskCategory;

    private String comments;

    public RiskSurveyDto() {
    }

    public RiskSurveyDto(Object[] columns) {
        this.projectId = (Integer) columns[0];
        this.projectName = (String) columns[1];
        this.accountName = (String) columns[2];
        this.indicatorColor = (String) columns[3];
        this.isWarrantyApplicable = (String) columns[4];
        this.liabilityLimits = (String) columns[5];
        this.contractType = (String) columns[6];
        this.customerCreditScore = (String) columns[7];
        this.sensitiveData = (String) columns[8];
        this.isComplianceAssociated = (String) columns[9];
        this.hardwareAndEquipment = (String) columns[10];
        this.isCovered = (String) columns[11];
        this.location = (String) columns[12];
        this.typeOfService = (String) columns[13];
        this.contractualDuration = (String) columns[14];
        this.isEstablishedAccount = (String) columns[15];
        this.totalProjectedRevenue = (String) columns[16];
        this.engagementWithTek = (String) columns[17];
        this.is3rdPartyDependent = (String) columns[18];
        this.hasUniqueChallenges = (String) columns[19];
        this.hasHighRiskCategory = (String) columns[20];
        this.comments = (String) columns[21];
    }

    public String getIndicatorColor() {
        return indicatorColor;
    }

    public void setIndicatorColor(String indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getIsWarrantyApplicable() {
        return isWarrantyApplicable;
    }

    public void setIsWarrantyApplicable(String isWarrantyApplicable) {
        this.isWarrantyApplicable = isWarrantyApplicable;
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
}
