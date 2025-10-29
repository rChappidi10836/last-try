package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.FilterDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Client")
@SqlResultSetMapping(name = "ClientMapping",
        classes = @ConstructorResult(
                targetClass = FilterDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "ClientName")}
        )
)

public class Client extends BaseDictionary {

    @Column(name = "ClientName")
    private String clientName;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "Active")
    private boolean active;

    @Column(name = "OpcoId")
    private Integer opcoid;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Project> projectList;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Opportunity> opportunityList;

    public List<Opportunity> getOpportunityList() {
        return opportunityList;
    }

    public void setOpportunityList(List<Opportunity> opportunityList) {
        this.opportunityList = opportunityList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getOpcoid() {
        return opcoid;
    }
    public void setOpcoid(Integer opcoid) {
        this.opcoid = opcoid;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
