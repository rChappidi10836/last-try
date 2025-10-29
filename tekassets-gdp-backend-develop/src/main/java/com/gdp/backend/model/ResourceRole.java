package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gdp.backend.dto.ResourceRoleDropdownDto;

import javax.persistence.*;
import java.util.List;

@Entity
@SqlResultSetMapping(name = "ResourceRoleDropdownMapping",
        classes = @ConstructorResult(
                targetClass = ResourceRoleDropdownDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "ResourceRoleName"),
                        @ColumnResult(name = "EngagementType")}
        )
)

public class ResourceRole extends Base {

    @Column(name = "ResourceRoleName")
    private String resourceRoleValue;

    @Column(name = "EngagementType")
    private String engagementType;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resourceRole", cascade = CascadeType.ALL)
    private List<ProjectResourceMapper> projectResourceMapperList;

    public String getResourceRoleValue() {
        return resourceRoleValue;
    }

    public void setResourceRoleValue(String resourceRoleValue) {
        this.resourceRoleValue = resourceRoleValue;
    }

    public List<ProjectResourceMapper> getProjectResourceMapperList() {
        return projectResourceMapperList;
    }

    public void setProjectResourceMapperList(List<ProjectResourceMapper> projectResourceMapperList) {
        this.projectResourceMapperList = projectResourceMapperList;
    }

    public String getEngagementType() {
        return engagementType;
    }

    public void setEngagementType(String engagementType) {
        this.engagementType = engagementType;
    }
}
