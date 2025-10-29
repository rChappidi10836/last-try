package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;
import com.gdp.backend.dto.FilterDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "PracticeEngagementDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "DeliveryOrganizationTypeName")}
        )
)

@SqlResultSetMapping(name = "PracticeEngagementMapping",
        classes = @ConstructorResult(
                targetClass = FilterDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "DeliveryOrganizationTypeName")}
        )
)

public class DeliveryOrganizationType extends BaseDictionary {

    @Column(name = "DeliveryOrganizationTypeName")
    private String deliveryOrganization;

    @Column(name = "Active")
    private boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryOrganizationType", cascade = CascadeType.ALL)
    private List<Project> projectList;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryOrganizationType", cascade = CascadeType.ALL)
    private List<Opportunity> opportunityList;

    public DeliveryOrganizationType() {
    }

    public DeliveryOrganizationType(Integer id, String deliveryOrganization, Boolean active) {
        setId(id);
        this.deliveryOrganization = deliveryOrganization;
        this.active = active;
    }

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }

    public String getDeliveryOrganization() {
        return deliveryOrganization;
    }

    public void setDeliveryOrganization(String deliveryOrganization) {
        this.deliveryOrganization = deliveryOrganization;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
