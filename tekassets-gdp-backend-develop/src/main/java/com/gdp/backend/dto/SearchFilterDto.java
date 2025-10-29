package com.gdp.backend.dto;

import java.util.Arrays;

public class SearchFilterDto {

    private Integer active;

    private Integer[] clientId;

    private Integer[] deliveryOrganizationTypeId;

    private Integer[] cslId;

    private Integer projectId;

    private Integer psId;

    private String orderByAttribute;

    private String sortDirection;

    private Integer[] gdmId;

    private Integer[] gddId;

    private Integer[] programManagerId;

    private Integer[] businessUnitId;

    private String[] dmId;

    public String[] getDmId() {
        return dmId;
    }

    public void setDmId(String[] dmId) {
        this.dmId = dmId;
    }

    public String[] getLodId() {
        return lodId;
    }

    public void setLodId(String[] lodId) {
        this.lodId = lodId;
    }

    private String[] lodId;

    public Integer[] getGdmId() {
		return gdmId;
	}

	public void setGdmId(Integer[] gdmId) {
		this.gdmId = gdmId;
	}

    public Integer[] getGddId() {
        return gddId;
    }

    public void setGddId(Integer[] gddId) {
        this.gddId = gddId;
    }

    public String getOrderByAttribute() {
        return orderByAttribute;
    }

    public void setOrderByAttribute(String orderByAttribute) {
        this.orderByAttribute = orderByAttribute;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer[] getClientId() {
        return clientId;
    }

    public void setClientId(Integer[] clientId) {
        this.clientId = clientId;
    }

    public Integer[] getDeliveryOrganizationTypeId() {
        return deliveryOrganizationTypeId;
    }

    public Integer[] getProgramManagerId() {
        return programManagerId;
    }

    public void setProgramManagerId(Integer[] programManagerId) {
        this.programManagerId = programManagerId;
    }

    public void setDeliveryOrganizationTypeId(Integer[] deliveryOrganizationTypeId) {
        this.deliveryOrganizationTypeId = deliveryOrganizationTypeId;
    }

    public Integer[] getCslId() {
        return cslId;
    }

    public void setCslId(Integer[] cslId) {
        this.cslId = cslId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer[] getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Integer[] businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    @Override
    public String toString() {
        return "SearchFilterDto{" +
                "active=" + active +
                ", clientId=" + Arrays.toString(clientId) +
                ", deliveryOrganizationTypeId=" + Arrays.toString(deliveryOrganizationTypeId) +
                ", cslId=" + Arrays.toString(cslId) +
                ", projectId=" + projectId +
                ", psId=" + psId +
                ", orderByAttribute='" + orderByAttribute + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", gdmId=" + Arrays.toString(gdmId) +
                ", gddId=" + Arrays.toString(gddId) +
                ", programManagerId=" + Arrays.toString(programManagerId) +
                ", businessUnitId=" + Arrays.toString(businessUnitId) +
                '}';
    }

}

