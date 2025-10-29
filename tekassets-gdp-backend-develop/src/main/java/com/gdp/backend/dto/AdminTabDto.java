package com.gdp.backend.dto;

public class AdminTabDto {

    private Integer id;

    private String name;

    private Boolean active;

    public Integer getOpcoId() {
        return opcoId;
    }

    public void setOpcoId(Integer opcoId) {
        this.opcoId = opcoId;
    }

    private Integer opcoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
