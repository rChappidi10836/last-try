package com.gdp.backend.enums;

public enum DeliveryRiskIndicator {

    HIGH("High"),
    MODERATE("Moderate"),
    LOW("Low");

    private String message;

    DeliveryRiskIndicator(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
