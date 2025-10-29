package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;
import com.gdp.backend.dto.FilterDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "BusinessUnitDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "BusinessUnitName")}
        )
)

@SqlResultSetMapping(name = "BusinessUnitFilterDropdownMapping",
        classes = @ConstructorResult(
                targetClass = FilterDto.class,
                columns = {
                        @ColumnResult(name = "Id"),
                        @ColumnResult(name = "BusinessUnitName")
                }
        )
)

public class BusinessUnit extends Base {

    @Column(name = "BusinessUnitName")
    private String businessUnitName;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessUnit", cascade = CascadeType.ALL)
    private List<Project> projectList;

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }
}
