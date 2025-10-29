package com.gdp.backend.dto;

public class ResourceDto {
    private Integer id;
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String username;
    private Integer opcoId;

    public Integer getOpcoId() {
        return opcoId;
    }

    public void setOpcoId(Integer opcoId) {
        this.opcoId = opcoId;
    }

    public ResourceDto() {
    }

    public ResourceDto(Integer id, String employeeId, String firstName, String middleName, String lastName, String fullName, String username, Integer opcoId) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.username = username;
        this.opcoId = opcoId;
    }

    public ResourceDto(Integer id, String employeeId, String firstName, String middleName, String lastName, String fullName, String username) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}