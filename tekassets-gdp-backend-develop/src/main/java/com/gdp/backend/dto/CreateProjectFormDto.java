package com.gdp.backend.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class CreateProjectFormDto {

    private Integer projectId;

    @NotNull
    private String projectName;

    @NotNull
    private Date startDate;

    @NotNull
    private String accountName;

    @NotNull
    private Date endDate;

    private String peopleSoftEngagementId;

    private Integer selectedSalesOrganizationId;

    private String peopleSoftId;

    private Integer selectedStaffingSaleRegionId;

    private String opportunityId;

    private Integer selectedBusinessId;

    private String gdpId;

    private String linkToSMPSite;

    private String modifiedBy;

    @NotNull
    private List<AddResourceDto> managerialInformation;

    @NotNull
    private List<OtherTeamMemberDto> otherTeamMembers;


    private Integer selectedPracticeEngagementId;

    @NotNull
    private Integer selectedOperatingCompanyId;

    private List<Integer> selectedLocationOfServicesId;

    @NotNull
    private Integer selectedDeliveryModelId;

    private Integer selectedContractTypeId;

    private List<Integer> selectedPrimaryServiceTypeId;

    private String deliveryOrganizationName;

    private String locationName;

    private Boolean isClosed;

    private Boolean isFlex;

    private String targetTechnologyPlatform;

    public Boolean getFlex() {
        return isFlex;
    }

    public void setFlex(Boolean flex) {
        isFlex = flex;
    }


    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getDeliveryOrganizationName() {
        return deliveryOrganizationName;
    }

    public void setDeliveryOrganizationName(String deliveryOrganizationName) {
        this.deliveryOrganizationName = deliveryOrganizationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<AddResourceDto> getManagerialInformation() {
        return managerialInformation;
    }

    public void setManagerialInformation(List<AddResourceDto> managerialInformation) {
        this.managerialInformation = managerialInformation;
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

    public String getPeopleSoftEngagementId() {
        return peopleSoftEngagementId;
    }

    public void setPeopleSoftEngagementId(String peopleSoftEngagementId) {
        this.peopleSoftEngagementId = peopleSoftEngagementId;
    }

    public Integer getSelectedSalesOrganizationId() {
        return selectedSalesOrganizationId;
    }

    public void setSelectedSalesOrganizationId(Integer selectedSalesOrganizationId) {
        this.selectedSalesOrganizationId = selectedSalesOrganizationId;
    }

    public String getPeopleSoftId() {
        return peopleSoftId;
    }

    public void setPeopleSoftId(String peopleSoftId) {
        this.peopleSoftId = peopleSoftId;
    }

    public Integer getSelectedStaffingSaleRegionId() {
        return selectedStaffingSaleRegionId;
    }

    public void setSelectedStaffingSaleRegionId(Integer selectedStaffingSaleRegionId) {
        this.selectedStaffingSaleRegionId = selectedStaffingSaleRegionId;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public Integer getSelectedBusinessId() {
        return selectedBusinessId;
    }

    public void setSelectedBusinessId(Integer selectedBusinessId) {
        this.selectedBusinessId = selectedBusinessId;
    }

    public String getGdpId() {
        return gdpId;
    }

    public void setGdpId(String gdpId) {
        this.gdpId = gdpId;
    }

    public String getLinkToSMPSite() {
        return linkToSMPSite;
    }

    public void setLinkToSMPSite(String linkToSMPSite) {
        this.linkToSMPSite = linkToSMPSite;
    }

    public Integer getSelectedPracticeEngagementId() {
        return selectedPracticeEngagementId;
    }

    public void setSelectedPracticeEngagementId(Integer selectedPracticeEngagementId) {
        this.selectedPracticeEngagementId = selectedPracticeEngagementId;
    }

    public Integer getSelectedOperatingCompanyId() {
        return selectedOperatingCompanyId;
    }

    public void setSelectedOperatingCompanyId(Integer selectedOperatingCompanyId) {
        this.selectedOperatingCompanyId = selectedOperatingCompanyId;
    }

    public List<Integer> getSelectedLocationOfServicesId() {
        return selectedLocationOfServicesId;
    }

    public void setSelectedLocationOfServicesId(List<Integer> selectedLocationOfServicesId) {
        this.selectedLocationOfServicesId = selectedLocationOfServicesId;
    }

    public Integer getSelectedDeliveryModelId() {
        return selectedDeliveryModelId;
    }

    public void setSelectedDeliveryModelId(Integer selectedDeliveryModelId) {
        this.selectedDeliveryModelId = selectedDeliveryModelId;
    }

    public Integer getSelectedContractTypeId() {
        return selectedContractTypeId;
    }

    public void setSelectedContractTypeId(Integer selectedContractTypeId) {
        this.selectedContractTypeId = selectedContractTypeId;
    }

    public List<Integer> getSelectedPrimaryServiceTypeId() {
        return selectedPrimaryServiceTypeId;
    }

    public void setSelectedPrimaryServiceTypeId(List<Integer> selectedPrimaryServiceTypeId) {
        this.selectedPrimaryServiceTypeId = selectedPrimaryServiceTypeId;
    }

    public List<OtherTeamMemberDto> getOtherTeamMembers() {
        return otherTeamMembers;
    }

    public void setOtherTeamMembers(List<OtherTeamMemberDto> otherTeamMembers) {
        this.otherTeamMembers = otherTeamMembers;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Boolean getIsFlex() {
        return isFlex;
    }

    public void setIsFlex(Boolean flex) {
        isFlex = flex;
    }

    public String getTargetTechnologyPlatform() {
        return targetTechnologyPlatform;
    }

    public void setTargetTechnologyPlatform(String targetTechnologyPlatform) {
        this.targetTechnologyPlatform = targetTechnologyPlatform;
    }
}
