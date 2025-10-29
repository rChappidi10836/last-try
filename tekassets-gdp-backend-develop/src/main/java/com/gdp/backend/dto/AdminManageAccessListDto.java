package com.gdp.backend.dto;

import java.util.List;

public class AdminManageAccessListDto {
    private Long activeTotalCount;
    private Long inactiveTotalCount;
    private List<AdminManageAccessDto> resources;

    public Long getActiveTotalCount() {
        return activeTotalCount;
    }

    public void setActiveTotalCount(Long activeTotalCount) {
        this.activeTotalCount = activeTotalCount;
    }

    public Long getInactiveTotalCount() {
        return inactiveTotalCount;
    }

    public void setInactiveTotalCount(Long inactiveTotalCount) {
        this.inactiveTotalCount = inactiveTotalCount;
    }

    public List<AdminManageAccessDto> getResources() {
        return resources;
    }

    public void setResources(List<AdminManageAccessDto> resources) {
        this.resources = resources;
    }
}
