package com.gdp.backend.dto;

public class SecurityProfileHeaderDto {

    private String projectName;

    private String accountName;

    private String deliveryRiskIndicator;

    private String globalDeliveryManager;

    private String customerSuccessLeader;

    private String securityProfileStatus;

    public String getSecurityProfileStatus() {
        return securityProfileStatus;
    }

    public void setSecurityProfileStatus(String securityProfileStatus) {
        this.securityProfileStatus = securityProfileStatus;
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

    public String getDeliveryRiskIndicator() {
        return deliveryRiskIndicator;
    }

    public void setDeliveryRiskIndicator(String deliveryRiskIndicator) {
        this.deliveryRiskIndicator = deliveryRiskIndicator;
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
}
