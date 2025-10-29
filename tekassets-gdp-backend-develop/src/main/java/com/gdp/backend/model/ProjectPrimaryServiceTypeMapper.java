package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProjectPrimaryServiceTypeMapper extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    @JsonBackReference
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PrimaryServiceTypeId")
    @JsonBackReference
    private PrimaryServiceType primaryServiceType;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PrimaryServiceType getPrimaryServiceType() {
        return primaryServiceType;
    }

    public void setPrimaryServiceType(PrimaryServiceType primaryServiceType) {
        this.primaryServiceType = primaryServiceType;
    }
}
