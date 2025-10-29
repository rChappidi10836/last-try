package com.gdp.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Scope extends BaseDictionary {

    @Column(name = "ScopeName")
    private String scopeName;

    @Column(name = "Active")
    private Boolean isActive;

    public Scope() {
    }

    public Scope(Integer id, String scopeName, Boolean isActive) {
        setId(id);
        this.scopeName = scopeName;
        this.isActive = isActive;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
