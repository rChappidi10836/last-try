package com.gdp.backend.dto;

import javax.validation.constraints.NotNull;

public class MailRequestDto {

    @NotNull
    private String projectName;

    @NotNull
    private String accountName;

    private String opportunityId;

    @NotNull
    private String gdpId;

    private String lastModifiedBy;

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getGdpId() {
        return gdpId;
    }

    public void setGdpId(String gdpId) {
        this.gdpId = gdpId;
    }

}
