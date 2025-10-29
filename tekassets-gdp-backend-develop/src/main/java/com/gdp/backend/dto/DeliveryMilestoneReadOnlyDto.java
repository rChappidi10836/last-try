package com.gdp.backend.dto;

import com.gdp.backend.common.Utils;

import java.util.Date;
import java.util.Objects;

public class DeliveryMilestoneReadOnlyDto {

    private Integer id;

    private String description;

    private String type;

    private Date startDate;

    private Date endDate;

    private String status;

    private String comments;

    private String lastUpdatedBy;

    private String lastUpdatedAt;

    private String weekEndingDate;

    private Boolean isActive;

    private int typeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(String weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
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

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryMilestoneReadOnlyDto)) return false;
        DeliveryMilestoneReadOnlyDto that = (DeliveryMilestoneReadOnlyDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(typeId, that.typeId) &&
                Objects.equals(Utils.getFormattedDate(startDate), Utils.getFormattedDate(that.startDate)) &&
                Objects.equals(Utils.getFormattedDate(endDate), Utils.getFormattedDate(that.endDate)) &&
                status.equals(that.status) &&
                Objects.equals(comments, that.comments) &&
                isActive.equals(that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, type, startDate, endDate, status, comments, isActive);
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
