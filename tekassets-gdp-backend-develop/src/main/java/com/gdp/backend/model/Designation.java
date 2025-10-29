package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdp.backend.dto.DesignationDto;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMapping(name = "DesignationDto", classes = @ConstructorResult(targetClass = DesignationDto.class,
        columns = {@ColumnResult(name = "Id"),
                @ColumnResult(name = "DesignationName")}))
@Entity
public class Designation extends Base {

    @Column(name = "DesignationName")
    private String designationName;

    @Column(name = "Active")
    private Boolean isActive;

    @Column(name = "Deleted")
    private boolean deleted;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designation", cascade = CascadeType.ALL)
    private List<Resource> resources;

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
