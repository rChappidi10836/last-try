package com.gdp.backend.dto;

import java.util.Date;

public class AdminManageAccessEditDto {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private String username;
    private String jobCode;
    private Integer designationId;
    private Integer supervisorId;
    private Date hireDate;
    private Date terminationDate;
    private Integer resourceLocationId;

    private String opcoId;

    @Override
    public String toString() {
        return "AdminManageAccessEditDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", username='" + username + '\'' +
                ", jobCode='" + jobCode + '\'' +
                ", designationId=" + designationId +
                ", supervisorId=" + supervisorId +
                ", hireDate=" + hireDate +
                ", terminationDate=" + terminationDate +
                ", resourceLocationId=" + resourceLocationId +
                ", opcoId='" + opcoId + '\'' +
                ", access='" + access + '\'' +
                ", active=" + active +
                '}';
    }

    private String access;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Integer getResourceLocationId() {
        return resourceLocationId;
    }

    public String getOpcoId() {
        return opcoId;
    }

    public void setOpcoId(String opcoId) {
        this.opcoId = opcoId;
    }

    public void setResourceLocationId(Integer resourceLocationId) {
        this.resourceLocationId = resourceLocationId;
    }

}