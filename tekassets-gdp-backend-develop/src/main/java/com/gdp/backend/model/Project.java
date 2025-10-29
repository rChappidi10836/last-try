package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gdp.backend.dto.FilterDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "ProjectMapping",
        classes = @ConstructorResult(
                targetClass = FilterDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "GDPId")}
        )
)

@SqlResultSetMapping(name = "ProjectToProjectIdMapping",
        classes = @ConstructorResult(
                targetClass = FilterDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "PeopleSoftProjectId")}
        )
)

public class Project extends Base {

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "GDPId")
    private String gdpId;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "SMPSite")
    private String smpSite;

    @Column(name = "PeopleSoftProjectId")
    private String peopleSoftProjectId;

    @Column(name = "PeopleSoftEngagementId")
    private String peopleSoftEngagementId;

    @ManyToOne
    @JoinColumn(name = "DeliveryOrganizationTypeID")
    @JsonManagedReference
    private DeliveryOrganizationType deliveryOrganizationType;

    @ManyToOne
    @JoinColumn(name = "ClientId")
    @JsonManagedReference
    private Client client;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectStatus> projectStatusList;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectResourceMapper> projectResourceMapperList;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<DeliveryMilestones> deliveryMilestonesList;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<AgileSprintMilestones> agileSprintMilestonesList;

    @ManyToOne
    @JoinColumn(name = "DeliveryModelId")
    @JsonManagedReference
    private DeliveryModel deliveryModel;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectOpportunityMapper> projectOpportunityMapperList;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPrimaryServiceTypeMapper> projectPrimaryServiceTypeMapperList;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectLocationMapper> projectLocationMapperList;

    @ManyToOne
    @JoinColumn(name = "ContractTypeId")
    @JsonManagedReference
    private ContractType contractType;

    @ManyToOne
    @JoinColumn(name = "SalesOrganizationId")
    @JsonManagedReference
    private SalesOrganization salesOrganization;

    @ManyToOne
    @JoinColumn(name = "BusinessUnitId")
    @JsonManagedReference
    private BusinessUnit businessUnit;

    @ManyToOne
    @JoinColumn(name = "StaffingSalesRegionId")
    @JsonManagedReference
    private StaffingSalesRegion staffingSalesRegion;

    @ManyToOne
    @JoinColumn(name = "LocationId")
    @JsonManagedReference
    private Location location;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPeopleSoftMapper> projectPeopleSoftMapperList;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "project")
    private ProjectRiskSurvey projectRiskSurvey;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectSecurityProfile> projectSecurityProfileList;

    @Column(name = "isFlex")
    private Boolean isFlex = false;
    @Column(name = "Target_Technology_Platform")
    private String targetTechnologyPlatform;

    public ProjectRiskSurvey getProjectRiskSurvey() {
        return projectRiskSurvey;
    }

    public void setProjectRiskSurvey(ProjectRiskSurvey projectRiskSurvey) {
        this.projectRiskSurvey = projectRiskSurvey;
    }

    public List<ProjectSecurityProfile> getProjectSecurityProfileList() {
        return projectSecurityProfileList;
    }

    public void setProjectSecurityProfileList(List<ProjectSecurityProfile> projectSecurityProfileList) {
        this.projectSecurityProfileList = projectSecurityProfileList;
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

    public DeliveryModel getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    public List<ProjectOpportunityMapper> getProjectOpportunityMapperList() {
        return projectOpportunityMapperList;
    }

    public void setProjectOpportunityMapperList(List<ProjectOpportunityMapper> projectOpportunityMapperList) {
        this.projectOpportunityMapperList = projectOpportunityMapperList;
    }

    public List<ProjectPrimaryServiceTypeMapper> getProjectPrimaryServiceTypeMapperList() {
        return projectPrimaryServiceTypeMapperList;
    }

    public void setProjectPrimaryServiceTypeMapperList(List<ProjectPrimaryServiceTypeMapper> projectPrimaryServiceTypeMapperList) {
        this.projectPrimaryServiceTypeMapperList = projectPrimaryServiceTypeMapperList;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public SalesOrganization getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(SalesOrganization salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public StaffingSalesRegion getStaffingSalesRegion() {
        return staffingSalesRegion;
    }

    public void setStaffingSalesRegion(StaffingSalesRegion staffingSalesRegion) {
        this.staffingSalesRegion = staffingSalesRegion;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<ProjectPeopleSoftMapper> getProjectPeopleSoftMapperList() {
        return projectPeopleSoftMapperList;
    }

    public void setProjectPeopleSoftMapperList(List<ProjectPeopleSoftMapper> projectPeopleSoftMapperList) {
        this.projectPeopleSoftMapperList = projectPeopleSoftMapperList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ProjectResourceMapper> getProjectResourceMapperList() {
        return projectResourceMapperList;
    }

    public void setProjectResourceMapperList(List<ProjectResourceMapper> projectResourceMapperList) {
        this.projectResourceMapperList = projectResourceMapperList;
    }

    public List<ProjectStatus> getProjectStatusList() {
        return projectStatusList;
    }

    public void setProjectStatusList(List<ProjectStatus> projectStatusList) {
        this.projectStatusList = projectStatusList;
    }

    public DeliveryOrganizationType getDeliveryOrganizationType() {
        return deliveryOrganizationType;
    }

    public void setDeliveryOrganizationType(DeliveryOrganizationType deliveryOrganizationType) {
        this.deliveryOrganizationType = deliveryOrganizationType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGdpId() {
        return gdpId;
    }

    public void setGdpId(String gdpId) {
        this.gdpId = gdpId;
    }

    public List<ProjectLocationMapper> getProjectLocationMapperList() {
        return projectLocationMapperList;
    }

    public void setProjectLocationMapperList(List<ProjectLocationMapper> projectLocationMapperList) {
        this.projectLocationMapperList = projectLocationMapperList;
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

    public Boolean getFlex() {
        return isFlex;
    }

    public void setFlex(Boolean flex) {
        isFlex = (flex!= null)?flex:false;
    }

    public String getTargetTechnologyPlatform() {
        return targetTechnologyPlatform;
    }

    public void setTargetTechnologyPlatform(String targetTechnologyPlatform) {
        this.targetTechnologyPlatform = targetTechnologyPlatform;
    }
}
