package com.gdp.backend.dto;

import java.util.List;

public class AgileSprintMilestoneDto {

    List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestonesReadOnlyDtoList;

    private Long totalCount;

    public AgileSprintMilestoneDto() {
    }

    public AgileSprintMilestoneDto(List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestonesReadOnlyDtoList, Long totalCount) {
        this.agileSprintMilestonesReadOnlyDtoList = agileSprintMilestonesReadOnlyDtoList;
        this.totalCount = totalCount;
    }

    public List<AgileSprintMilestoneReadOnlyDto> getAgileSprintMilestonesReadOnlyDtoList() {
        return agileSprintMilestonesReadOnlyDtoList;
    }

    public void setAgileSprintMilestonesReadOnlyDtoList(List<AgileSprintMilestoneReadOnlyDto> agileSprintMilestonesReadOnlyDtoList) {
        this.agileSprintMilestonesReadOnlyDtoList = agileSprintMilestonesReadOnlyDtoList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
