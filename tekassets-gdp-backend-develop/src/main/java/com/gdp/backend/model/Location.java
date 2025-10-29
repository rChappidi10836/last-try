package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.CreateProjectDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "LocationOfServicesDropdownMapping",
        classes = @ConstructorResult(
                targetClass = CreateProjectDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "LocationName")}
        )
)

public class Location extends BaseDictionary {

    @Column(name = "LocationName")
    private String locationName;

    @Column(name = "Active")
    private boolean active;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.ALL)
    private List<Project> projectList;

    public Location() {
    }

    public Location(Integer id, String locationName, Boolean active) {
        setId(id);
        this.locationName = locationName;
        this.active = active;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
