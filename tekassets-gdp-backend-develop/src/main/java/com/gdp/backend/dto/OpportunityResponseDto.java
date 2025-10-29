package com.gdp.backend.dto;

import java.util.List;

public class OpportunityResponseDto {

    private Long totalNumberOfElements;

    private List<OpportunityDto> opportunityDtoList;

    public Long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public void setTotalNumberOfElements(Long totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<OpportunityDto> getOpportunityDtoList() {
        return opportunityDtoList;
    }

    public void setOpportunityDtoList(List<OpportunityDto> opportunityDtoList) {
        this.opportunityDtoList = opportunityDtoList;
    }
}
