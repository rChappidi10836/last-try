package com.gdp.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class DeliveryHoursRequestDTO {
    private Integer projectId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date weekEndingDate;

    private List<ResourceHoursDTO> resourceHours;
    
    public DeliveryHoursRequestDTO() {}

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public List<ResourceHoursDTO> getResourceHours() {
        return resourceHours;
    }

    public void setResourceHours(List<ResourceHoursDTO> resourceHours) {
        this.resourceHours = resourceHours;
    }
}
