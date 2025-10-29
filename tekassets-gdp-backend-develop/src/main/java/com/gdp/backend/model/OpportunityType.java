package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class OpportunityType extends Base {

    @Column(name = "opportunityTypeName")
    private String opportunityTypeName;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "opportunityType", cascade = CascadeType.ALL)
    private List<Opportunity> opportunityList;

    public String getOpportunityTypeName() {
        return opportunityTypeName;
    }

    public void setOpportunityTypeName(String opportunityTypeName) {
        this.opportunityTypeName = opportunityTypeName;
    }

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }
}
