package com.gdp.backend.dto;

import java.util.List;

public class GDPIdDto {
    private List<FilterDto> gdpIdList;

    public List<FilterDto> getGdpIdList() {
        return gdpIdList;
    }

    public GDPIdDto() {
    }
    public GDPIdDto(List<FilterDto> gdpIdList) {
        super();
        this.gdpIdList = gdpIdList;
    }



    public void setGdpIdList(List<FilterDto> gdpIdList) {
        this.gdpIdList = gdpIdList;
    }

    @Override
    public String toString() {
        return "GDPIdDto{" +
                "gdpIdList=" + gdpIdList +
                '}';
    }
}
