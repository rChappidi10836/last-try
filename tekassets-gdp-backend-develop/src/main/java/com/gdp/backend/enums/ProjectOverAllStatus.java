package com.gdp.backend.enums;

public enum ProjectOverAllStatus {

    RED("Red", "3Red"),
    YELLOW("Yellow", "2Yellow"),
    GREEN("Green", "1Green");

    private String status;

    private String statusForFrontend;

    ProjectOverAllStatus(String status, String statusForFrontend) {
        this.status = status;
        this.statusForFrontend = statusForFrontend;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusForFrontend() {
        return statusForFrontend;
    }
}
