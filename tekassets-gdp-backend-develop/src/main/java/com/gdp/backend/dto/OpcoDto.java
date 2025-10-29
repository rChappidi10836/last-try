package com.gdp.backend.dto;

public class OpcoDto {

    private Integer id;

    public OpcoDto() {
    }

    private String OpcoName;

    public OpcoDto(Integer id, String opcoName) {
        this.id = id;
        OpcoName = opcoName;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getOpcoName() {return OpcoName;}

    public void setOpcoName(String opcoName) {
        OpcoName = opcoName;}
}
