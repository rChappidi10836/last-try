package com.gdp.backend.dto;

import com.gdp.backend.common.Utils;

import java.util.Date;

public class DeliveryHoursDto {
    private Integer projectId;
    private Integer resourceId;
    private Integer id;
    private String name;
    private Integer hours;
    private Date weekEndingDate;
    private String resourceRole;

    public DeliveryHoursDto() {
    }

    public DeliveryHoursDto(Integer id, String fName, String mName, String lName, Integer hours, Date weekEndingDate, Integer resourceId, String resourceRole) {
        this.id = id;
        this.name = Utils.getFullName(fName, mName, lName);
        this.hours = hours;
        this.weekEndingDate = weekEndingDate;
        this.resourceId = resourceId;
        this.resourceRole = resourceRole;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public String getResourceRole() {
        return resourceRole;
    }

    public void setResourceRole(String resourceRole) {
        this.resourceRole = resourceRole;
    }
}
