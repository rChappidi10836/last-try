package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "SalesOrganizationDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "SalesOrganizationName")}
        )
)

public class SalesOrganization extends BaseDictionary {

    @Column(name = "SalesOrganizationName")
    private String salesOrganizationName;

    @Column(name = "Active")
    private boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salesOrganization", cascade = CascadeType.ALL)
    private List<Project> projectList;

    public SalesOrganization() {
    }

    public SalesOrganization(Integer id, String salesOrganizationName, Boolean active) {
        setId(id);
        this.salesOrganizationName = salesOrganizationName;
        this.active = active;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getSalesOrganizationName() {
        return salesOrganizationName;
    }

    public void setSalesOrganizationName(String salesOrganizationName) {
        this.salesOrganizationName = salesOrganizationName;
    }
}
