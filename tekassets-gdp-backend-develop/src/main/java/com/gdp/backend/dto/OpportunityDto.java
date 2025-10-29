package com.gdp.backend.dto;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class OpportunityDto {

    private Integer opportunityPk;

    private Date unformattedStartDate;

    private Date unformattedClosedDate;

    private Integer primaryServiceTypeId;

    private Integer deliveryModelId;

    private Integer deliveryOrganizationTypeId;

    private Integer salesOrganizationId;

    private String opportunityName;

    private String accountName;

    private String opportunityId;

    private String startDate;

    private String closedDate;

    private String gsOpportunity;

    private String primaryService;

    private String deliveryModel;

    private String practice;

    private Integer LocationId;

    private String Target_Technology_Platform;



    public OpportunityDto() {
    }

    public OpportunityDto(Object[] columns) {
        this.opportunityPk = (Integer) columns[0];
        this.unformattedStartDate = (Date) columns[7];
        this.unformattedClosedDate = (Date) columns[8];
        this.primaryServiceTypeId = (Integer) columns[1];
        this.deliveryModelId = (Integer) columns[2];
        this.deliveryOrganizationTypeId = (Integer) columns[3];
        this.opportunityName = (String) columns[4];
        this.accountName = (String) columns[5];
        this.opportunityId = (String) columns[6];
        this.startDate = getFormattedDate((Date) columns[7]);
        this.closedDate = getFormattedDate((Date) columns[8]);
        this.gsOpportunity = (String) columns[9];
        this.primaryService = (String) columns[10];
        this.deliveryModel = (String) columns[11];
        this.practice = (String) columns[12];
        this.salesOrganizationId = (Integer)columns[13];
        this.LocationId=(Integer)columns[14];
        this.Target_Technology_Platform = (String)columns[15];
    }

    public static String getFormattedDate(Date date) {
        if(date==null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }

    public Integer getOpportunityPk() {
        return opportunityPk;
    }

    public void setOpportunityPk(Integer opportunityPk) {
        this.opportunityPk = opportunityPk;
    }

    public Date getUnformattedStartDate() {
        return unformattedStartDate;
    }

    public void setUnformattedStartDate(Date unformattedStartDate) {
        this.unformattedStartDate = unformattedStartDate;
    }

    public Date getUnformattedClosedDate() {
        return unformattedClosedDate;
    }

    public void setUnformattedClosedDate(Date unformattedClosedDate) {
        this.unformattedClosedDate = unformattedClosedDate;
    }

    public Integer getPrimaryServiceTypeId() {
        return primaryServiceTypeId;
    }

    public void setPrimaryServiceTypeId(Integer primaryServiceTypeId) {
        this.primaryServiceTypeId = primaryServiceTypeId;
    }

    public Integer getDeliveryModelId() {
        return deliveryModelId;
    }

    public void setDeliveryModelId(Integer deliveryModelId) {
        this.deliveryModelId = deliveryModelId;
    }

    public Integer getDeliveryOrganizationTypeId() {
        return deliveryOrganizationTypeId;
    }

    public void setDeliveryOrganizationTypeId(Integer deliveryOrganizationTypeId) {
        this.deliveryOrganizationTypeId = deliveryOrganizationTypeId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = getFormattedDate(startDate);
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = getFormattedDate(closedDate);
    }

    public String getGsOpportunity() {
        return gsOpportunity;
    }

    public void setGsOpportunity(String gsOpportunity) {
        this.gsOpportunity = gsOpportunity;
    }

    public String getPrimaryService() {
        return primaryService;
    }

    public void setPrimaryService(String primaryService) {
        this.primaryService = primaryService;
    }

    public String getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(String deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public Integer getSalesOrganizationId() {
        return salesOrganizationId;
    }

    public void setSalesOrganizationId(Integer salesOrganizationId) {
        this.salesOrganizationId = salesOrganizationId;
    }
    public Integer getLocationId() {
        return LocationId;
    }

    public void setLocationId(Integer locationId) {
        LocationId = locationId;
    }

    public String getTarget_Technology_Platform() {
        return Target_Technology_Platform;
    }

    public void setTarget_Technology_Platform(String target_Technology_Platform) {
        Target_Technology_Platform = target_Technology_Platform;
    }

}
