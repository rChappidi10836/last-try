package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ResourceLocation extends Base {

    @Column(name = "LocationName")
    private String locationName;

    @Column(name = "Active")
    private Boolean isActive;

    @Column(name = "Deleted")
    private boolean deleted;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resourceLocation", cascade = CascadeType.ALL)
    private List<Resource> resources;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}

