package com.gdp.backend.dto;

public class UserSearchDto {

    private Integer resourceId;

    private String firstName;

    private String lastName;

    private String userId;

    private String fullName;


    public UserSearchDto() {
    }

    public UserSearchDto(Object[] columns, String fullName) {
        this.resourceId = (Integer) columns[0];
        this.firstName = (String) columns[1];
        this.lastName = (String) columns[3];
        this.fullName = fullName;
        this.userId = (String) columns[4];
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
