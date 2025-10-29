package com.gdp.backend.dto;

public class ResourceHoursDTO {
    private Integer resourceId;
    private Integer id;
    private String name;
    private String hours;
    
    public ResourceHoursDTO() {}

    public ResourceHoursDTO(Integer id, String name, String hours, Integer resourceId) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.resourceId = resourceId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
