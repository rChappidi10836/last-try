package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gdp.backend.dto.ResourceDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SqlResultSetMapping(name = "ResourceDto", classes = @ConstructorResult(targetClass = ResourceDto.class,
        columns = {@ColumnResult(name = "Id"),
                @ColumnResult(name = "EmployeeId"),
                @ColumnResult(name = "FirstName"),
                @ColumnResult(name = "MiddleName"),
                @ColumnResult(name = "LastName"),
                @ColumnResult(name = "EmployeeName"),
                @ColumnResult(name = "UserId"),
                @ColumnResult(name = "OpcoId")}))
@Entity
@Table
public class Resource extends Base {

    @Column(name = "EmployeeId")
    private String employeeId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "MiddleName")
    private String middleName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "UserId")
    private String userId;


    @ManyToOne
    @JoinColumn(name = "DesignationId")
    @JsonManagedReference
    private Designation designation;

    @ManyToOne
    @JoinColumn(name = "ResourceLocationId")
    @JsonManagedReference
    private ResourceLocation resourceLocation;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "HireDate")
    private Date hireDate;

    @Column(name = "TerminationDate")
    private Date terminationDate;

    @ManyToOne
    @JoinColumn(name = "SupervisorId")
    @JsonBackReference
    private Resource supervisor;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<Resource> resources;

    @Column(name = "JobCode")
    private String jobCode;

    @Column(name = "Access")
    private String access;

    @Column(name = "Deleted")
    private String deleted;

    @Column(name = "OpcoId")
    private String opcoid;


    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resource", cascade = CascadeType.ALL)
    private List<ProjectResourceMapper> projectResourceMapperList;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ProjectResourceMapper> getProjectResourceMapperList() {
        return projectResourceMapperList;
    }

    public void setProjectResourceMapperList(List<ProjectResourceMapper> projectResourceMapperList) {
        this.projectResourceMapperList = projectResourceMapperList;
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

    public Resource getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Resource supervisor) {
        this.supervisor = supervisor;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getOpcoid() {return opcoid;}
    public void setOpcoid(String opcoid) {this.opcoid = opcoid;}
}
