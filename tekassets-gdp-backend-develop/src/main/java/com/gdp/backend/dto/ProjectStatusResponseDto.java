package com.gdp.backend.dto;

import java.util.Set;

public class ProjectStatusResponseDto {

    private Long totalNumberOfElements;

    private Set<ProjectStatusDto> projectStatusDtoList;

    public Long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public void setTotalNumberOfElements(Long totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public Set<ProjectStatusDto> getProjectStatusDtoList() {
        return projectStatusDtoList;
    }

    public void setProjectStatusDtoList(Set<ProjectStatusDto> projectStatusDtoList) {
        this.projectStatusDtoList = projectStatusDtoList;
    }
}
