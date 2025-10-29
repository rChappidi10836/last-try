package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "ContractTypeDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "ContractTypeName")}
        )
)

public class ContractType extends BaseDictionary {

    @Column(name = "ContractTypeName")
    private String contractTypeName;

    @Column(name = "Active")
    private boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contractType", cascade = CascadeType.ALL)
    private List<Project> projectList;

    public ContractType() {
    }

    public ContractType(Integer id, String contractTypeName, Boolean active) {
        setId(id);
        this.contractTypeName = contractTypeName;
        this.active = active;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

}
