package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "DeliveryModelDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "DeliveryModelName")}
        )
)

public class DeliveryModel extends BaseDictionary {

    @Column(name = "DeliveryModelName")
    private String deliveryModelName;

    @Column(name = "Active")
    private Boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryModel", cascade = CascadeType.ALL)
    private List<Opportunity> opportunityList;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryModel", cascade = CascadeType.ALL)
    private List<Project> projectList;

    public DeliveryModel() {
    }

    public DeliveryModel(Integer id, String deliveryModelName, Boolean active) {
        setId(id);
        this.deliveryModelName = deliveryModelName;
        this.active = active;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getDeliveryModelName() {
        return deliveryModelName;
    }

    public void setDeliveryModelName(String deliveryModelName) {
        this.deliveryModelName = deliveryModelName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }
}
