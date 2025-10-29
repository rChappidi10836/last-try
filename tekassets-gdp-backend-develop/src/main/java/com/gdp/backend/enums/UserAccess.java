package com.gdp.backend.enums;

public enum UserAccess {
    ADMIN("Admin"),
    GLOBAL_EDIT_ACCESS("Global Edit Access"),
    GLOBAL_VIEW_ACCESS("Global View Access"),

    EXECUTIVE("Executive"),

    REVOKE_ACCESS("Revoke Access");



    private String userAccess;

    UserAccess(String userAccess) {
        this.userAccess = userAccess;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }
}
