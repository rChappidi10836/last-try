package com.gdp.backend.dto;

import java.util.Date;

public class AdminManageAccessDto {
    private Integer id;
    private String employeeName;
    private String employeeId;
    private String username;
    private String jobCode;
    private String jobTitle;
    private String supervisor;
    private String location;
    private Date hireDate;
    private Date terminationDate;
    private String access;
    private boolean active;
    private String opcoName;
    private Integer resourceLocationId;


    @Override
    public String toString() {
        return "AdminManageAccessDto{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", username='" + username + '\'' +
                ", jobCode='" + jobCode + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", location='" + location + '\'' +
                ", hireDate=" + hireDate +
                ", terminationDate=" + terminationDate +
                ", access='" + access + '\'' +
                ", active=" + active +
                ", opcoName='" + opcoName + '\'' +
                ", resourceLocationId=" + resourceLocationId +
                '}';
    }

    public String getOpcoName() {
        return opcoName;
    }

    public void setOpcoName(String opcoName) {
        this.opcoName= opcoName;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getResourceLocationId() { return resourceLocationId;}

    public void setResourceLocationId(Integer resourceLocationId) {this.resourceLocationId = resourceLocationId;}
}
