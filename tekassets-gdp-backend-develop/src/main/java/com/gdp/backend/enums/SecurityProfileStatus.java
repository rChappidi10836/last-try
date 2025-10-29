package com.gdp.backend.enums;

public enum SecurityProfileStatus {

    NOT_COMPLETED("Not Completed"),
    UPDATED("Updated"),
    PASS_DUE("Past Due");

    private String message;

    SecurityProfileStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
