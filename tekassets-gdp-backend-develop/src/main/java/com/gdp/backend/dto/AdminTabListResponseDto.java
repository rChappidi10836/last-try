package com.gdp.backend.dto;

import java.util.List;

public class AdminTabListResponseDto {

    private Long activeTotalCount;

    private Long inActiveTotalCount;

    private List<AdminTabDto> adminTabDtoList;

    public Long getActiveTotalCount() {
        return activeTotalCount;
    }

    public void setActiveTotalCount(Long activeTotalCount) {
        this.activeTotalCount = activeTotalCount;
    }

    public Long getInActiveTotalCount() {
        return inActiveTotalCount;
    }

    public void setInActiveTotalCount(Long inActiveTotalCount) {
        this.inActiveTotalCount = inActiveTotalCount;
    }

    public List<AdminTabDto> getAdminTabDtoList() {
        return adminTabDtoList;
    }

    public void setAdminTabDtoList(List<AdminTabDto> adminTabDtoList) {
        this.adminTabDtoList = adminTabDtoList;
    }
}
