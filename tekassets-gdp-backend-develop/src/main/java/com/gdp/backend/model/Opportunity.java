package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Opportunity extends Base {

    @Column(name = "OpportunityName")
    private String opportunityName;

    @Column(name = "OpportunityId")
    private String opportunityId;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "ClosedDate")
    private Date closedDate;

    @Column(name = "HasProject")
    private Boolean hasProject;

    @Column(name ="Target_Technology_Platform")
    private String targetTechnologyPlatform;



    @ManyToOne
    @JoinColumn(name = "DeliveryOrganizationTypeId")
    @JsonManagedReference
    private DeliveryOrganizationType deliveryOrganizationType;

    @ManyToOne
    @JoinColumn(name = "GSOpportunityTypeId")
    @JsonManagedReference
    private OpportunityType opportunityType;

    @ManyToOne
    @JoinColumn(name = "DeliveryModelId")
    @JsonManagedReference
    private DeliveryModel deliveryModel;

    @ManyToOne
    @JoinColumn(name = "PrimaryServiceTypeId")
    @JsonManagedReference
    private PrimaryServiceType primaryServiceType;

    @ManyToOne
    @JoinColumn(name = "ClientId")
    @JsonManagedReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "SalesOrganizationId")
    @JsonManagedReference
    private SalesOrganization salesOrganization;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "opportunity", cascade = CascadeType.ALL)
    private List<ProjectOpportunityMapper> projectOpportunityMapperList;

    public List<ProjectOpportunityMapper> getProjectOpportunityMapperList() {
        return projectOpportunityMapperList;
    }

    public void setProjectOpportunityMapperList(List<ProjectOpportunityMapper> projectOpportunityMapperList) {
        this.projectOpportunityMapperList = projectOpportunityMapperList;
    }

    public Boolean isHasProject() {
        return hasProject;
    }

    public void setHasProject(Boolean hasProject) {
        this.hasProject = hasProject;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public DeliveryOrganizationType getDeliveryOrganizationType() {
        return deliveryOrganizationType;
    }

    public void setDeliveryOrganizationType(DeliveryOrganizationType deliveryOrganizationType) {
        this.deliveryOrganizationType = deliveryOrganizationType;
    }

    public OpportunityType getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(OpportunityType opportunityType) {
        this.opportunityType = opportunityType;
    }

    public DeliveryModel getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    public PrimaryServiceType getPrimaryServiceType() {
        return primaryServiceType;
    }

    public void setPrimaryServiceType(PrimaryServiceType primaryServiceType) {
        this.primaryServiceType = primaryServiceType;
    }

    public SalesOrganization getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(SalesOrganization salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public String getTargetTechnologyPlatform() {
        return targetTechnologyPlatform;
    }

    public void setTargetTechnologyPlatform(String targetTechnologyPlatform) {
        this.targetTechnologyPlatform = targetTechnologyPlatform;
    }

}