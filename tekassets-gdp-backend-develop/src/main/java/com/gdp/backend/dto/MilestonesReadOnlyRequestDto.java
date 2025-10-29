package com.gdp.backend.dto;

public class MilestonesReadOnlyRequestDto {

    private Integer projectId;

    private Integer pageNumber;

    private Integer pageSize;

    public MilestonesReadOnlyRequestDto() {
    }

    public MilestonesReadOnlyRequestDto(Integer projectId, Integer pageNumber, Integer pageSize) {
        this.projectId = projectId;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}
