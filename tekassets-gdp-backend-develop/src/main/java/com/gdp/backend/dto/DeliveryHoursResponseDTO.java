package com.gdp.backend.dto;

import java.util.List;

public class DeliveryHoursResponseDTO {
    private String weekEndingDate;
    private List<ResourceHoursDTO> resourceHours;
    private List<ResourceHoursDTO> deliveryLeaders;

    public DeliveryHoursResponseDTO() {
    }

    public DeliveryHoursResponseDTO(String weekEndingDate, List<ResourceHoursDTO> resourceHours, List<ResourceHoursDTO> deliveryLeaders) {
        this.weekEndingDate = weekEndingDate;
        this.resourceHours = resourceHours;
        this.deliveryLeaders = deliveryLeaders;
    }

    public String getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(String weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public List<ResourceHoursDTO> getResourceHours() {
        return resourceHours;
    }

    public void setResourceHours(List<ResourceHoursDTO> resourceHours) {
        this.resourceHours = resourceHours;
    }

    public List<ResourceHoursDTO> getDeliveryLeaders() {
        return deliveryLeaders;
    }

    public void setDeliveryLeaders(List<ResourceHoursDTO> deliveryLeaders) {
        this.deliveryLeaders = deliveryLeaders;
    }
}
