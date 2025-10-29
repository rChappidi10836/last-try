package com.gdp.backend.dto;

import java.util.Date;
import java.util.List;

public class StatusReportDto {

    private String projectName;

    private String accountName;

    private String securityProfileStatus;

    private String deliveryRiskIndicator;

    private Long totalCount;

    private String lastUpdatedBy;

    private Date lastUpdatedAt;

    private List<StatusReportWeeklyStatusDto> weeklyStatus;

    public String getSecurityProfileStatus() {
        return securityProfileStatus;
    }

    public void setSecurityProfileStatus(String securityProfileStatus) {
        this.securityProfileStatus = securityProfileStatus;
    }

    public String getDeliveryRiskIndicator() {
        return deliveryRiskIndicator;
    }

    public void setDeliveryRiskIndicator(String deliveryRiskIndicator) {
        this.deliveryRiskIndicator = deliveryRiskIndicator;
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

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<StatusReportWeeklyStatusDto> getWeeklyStatus() {
        return weeklyStatus;
    }

    public void setWeeklyStatus(List<StatusReportWeeklyStatusDto> weeklyStatus) {
        this.weeklyStatus = weeklyStatus;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
