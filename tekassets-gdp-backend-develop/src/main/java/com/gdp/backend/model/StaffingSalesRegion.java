package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "StaffingSalesRegionDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "StaffingSalesRegionName")}
        )
)

public class StaffingSalesRegion extends BaseDictionary {

    @Column(name = "StaffingSalesRegionName")
    private String staffingSalesRegionName;

    @Column(name = "Active")
    private boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "staffingSalesRegion", cascade = CascadeType.ALL)
    private List<Project> projectList;

    public StaffingSalesRegion() {
    }

    public StaffingSalesRegion(Integer id, String staffingSalesRegionName, Boolean active) {
        setId(id);
        this.staffingSalesRegionName = staffingSalesRegionName;
        this.active = active;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getStaffingSalesRegionName() {
        return staffingSalesRegionName;
    }

    public void setStaffingSalesRegionName(String staffingSalesRegionName) {
        this.staffingSalesRegionName = staffingSalesRegionName;
    }
}
