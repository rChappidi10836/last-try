package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "PrimaryServiceTypeDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "PrimaryServiceTypeName")}
        )
)

public class PrimaryServiceType extends BaseDictionary {

    @Column(name = "PrimaryServiceTypeName")
    private String primaryServiceTypeName;

    @Column(name = "Active")
    private Boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryServiceType", cascade = CascadeType.ALL)
    private List<Opportunity> opportunityList;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryServiceType", cascade = CascadeType.ALL)
    private List<ProjectPrimaryServiceTypeMapper> projectPrimaryServiceTypeMapperList;

    public PrimaryServiceType() {
    }

    public PrimaryServiceType(Integer id, String primaryServiceTypeName, Boolean active) {
        setId(id);
        this.primaryServiceTypeName = primaryServiceTypeName;
        this.active = active;
    }

    public String getPrimaryServiceTypeName() {
        return primaryServiceTypeName;
    }

    public void setPrimaryServiceTypeName(String primaryServiceTypeName) {
        this.primaryServiceTypeName = primaryServiceTypeName;
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
