package com.gdp.backend.dto;

import java.util.List;

public class DeliveryHoursWeeklyResponseDTO {
    private List<ResourceHoursDTO> resourceHours;

    public DeliveryHoursWeeklyResponseDTO() {
    }

    public DeliveryHoursWeeklyResponseDTO(List<ResourceHoursDTO> resourceHours) {
        this.resourceHours = resourceHours;
    }

    public List<ResourceHoursDTO> getResourceHours() {
        return resourceHours;
    }

    public void setResourceHours(List<ResourceHoursDTO> resourceHours) {
        this.resourceHours = resourceHours;
    }
}
