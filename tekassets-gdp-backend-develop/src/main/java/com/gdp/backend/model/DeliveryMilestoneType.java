package com.gdp.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DeliveryMilestoneType extends BaseDictionary {

    @Column(name = "DeliveryMilestoneTypeName")
    private String deliveryMilestoneTypeName;

    @Column(name = "Active")
    private Boolean isActive;

    public DeliveryMilestoneType() {
    }

    public DeliveryMilestoneType(Integer id, String deliveryMilestoneTypeName, Boolean isActive) {
        setId(id);
        this.deliveryMilestoneTypeName = deliveryMilestoneTypeName;
        this.isActive = isActive;
    }

    public String getDeliveryMilestoneTypeName() {
        return deliveryMilestoneTypeName;
    }

    public void setDeliveryMilestoneTypeName(String deliveryMilestoneTypeName) {
        this.deliveryMilestoneTypeName = deliveryMilestoneTypeName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
