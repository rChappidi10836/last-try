package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdp.backend.util.CurrentUsernameUtil;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @JsonIgnore
    @Column(name = "CreatedBy", insertable = true, updatable = false)
    private String createdByUser;

    @JsonIgnore
    @Column(name = "ModifiedBy", updatable = true)
    private String lastModifiedByUser;

    @Column(name = "CreatedAt", insertable = true, updatable = false)
    private Date createdTime;

    @Column(name = "ModifiedAt", updatable = true)
    private Date modifiedTime;


    @PrePersist
    public void prePersist() {
        Date now = new Date();
        setCreatedTime(now);
        setModifiedTime(now);
        setCreatedByUser(CurrentUsernameUtil.getCurrentUsername());
        setLastModifiedByUser(CurrentUsernameUtil.getCurrentUsername());
    }

    @PreUpdate
    public void preUpdate() {
        setModifiedTime(new Date());
        setLastModifiedByUser(CurrentUsernameUtil.getCurrentUsername());
    }

    public Base() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getLastModifiedByUser() {
        return lastModifiedByUser;
    }

    public void setLastModifiedByUser(String lastModifiedByUser) {
        this.lastModifiedByUser = lastModifiedByUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}



