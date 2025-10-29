package com.gdp.backend.dto;

public class DesignationDto {
    private Integer id;
    private String designationName;

    public DesignationDto() {
    }

    public DesignationDto(Integer id, String designationName) {
        this.id = id;
        this.designationName = designationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }
}

