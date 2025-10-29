package com.gdp.backend.dto;

import java.util.List;

public class DeliveryMilestoneDto {

    List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneReadOnlyDtoList;

    private Long totalCount;

    public DeliveryMilestoneDto() {
    }

    public DeliveryMilestoneDto(List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneReadOnlyDtoList, Long totalCount) {
        this.deliveryMilestoneReadOnlyDtoList = deliveryMilestoneReadOnlyDtoList;
        this.totalCount = totalCount;
    }

    public List<DeliveryMilestoneReadOnlyDto> getDeliveryMilestoneReadOnlyDtoList() {
        return deliveryMilestoneReadOnlyDtoList;
    }

    public void setDeliveryMilestoneReadOnlyDtoList(List<DeliveryMilestoneReadOnlyDto> deliveryMilestoneReadOnlyDtoList) {
        this.deliveryMilestoneReadOnlyDtoList = deliveryMilestoneReadOnlyDtoList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
