package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DeliveryMilestones extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    @JsonBackReference
    private Project project;

    @Column(name = "WeekEndingDate")
    private Date weekEndingDate;

    @Column(name = "Description")
    private String description;

    @Column(name = "Type")
    private String type;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "Status")
    private String status;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "Active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "DeliveryMilestoneTypeId")
    @JsonManagedReference
    private DeliveryMilestoneType deliveryMilestoneType;

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public DeliveryMilestoneType getDeliveryMilestoneType() {
        return deliveryMilestoneType;
    }

    public void setDeliveryMilestoneType(DeliveryMilestoneType deliveryMilestoneType) {
        this.deliveryMilestoneType = deliveryMilestoneType;
    }

}
