package com.gdp.backend.dto;

import io.swagger.models.auth.In;

public class FilterDto {

    private Integer id;

    private String name;


    public FilterDto() {
    }

    public FilterDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public FilterDto(Object[] columns, String name) {
        this.id = (Integer) columns[0];
        this.name = name;
    }

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
}
