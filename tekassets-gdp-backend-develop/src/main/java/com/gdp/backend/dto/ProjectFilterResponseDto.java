package com.gdp.backend.dto;


import java.util.List;

public class ProjectFilterResponseDto {

    private List<FilterDto> accountNameList;

    private List<FilterDto> practiceEngagementList;

    private List<FilterDto> cslList;

    private List<FilterDto> gdpIdList;
    
    private List<FilterDto> gdmList;


    private List<FilterDto> gddList;


    private List<FilterDto> programManager;
    private List<FilterDto> businessUnitList;

    private List<FilterDto> psProjectIdList;

    private List<FilterDto> deliverymodelList;

    public List<FilterDto> getDeliverymodelList() {
        return deliverymodelList;
    }

    public void setDeliverymodelList(List<FilterDto> deliverymodelList) {
        this.deliverymodelList = deliverymodelList;
    }

    public List<LocationDTO> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationDTO> locationList) {
        this.locationList = locationList;
    }

    private List<LocationDTO> locationList;

    public List<FilterDto> getGdmList() {
		return gdmList;
	}

	public void setGdmList(List<FilterDto> gdmList) {
		this.gdmList = gdmList;
	}

	public List<FilterDto> getAccountNameList() {
        return accountNameList;
    }

    public void setAccountNameList(List<FilterDto> accountNameList) {
        this.accountNameList = accountNameList;
    }

    public List<FilterDto> getPracticeEngagementList() {
        return practiceEngagementList;
    }

    public void setPracticeEngagementList(List<FilterDto> practiceEngagementList) {
        this.practiceEngagementList = practiceEngagementList;
    }

    public List<FilterDto> getCslList() {
        return cslList;
    }

    public void setCslList(List<FilterDto> cslList) {
        this.cslList = cslList;
    }

    public List<FilterDto> getGdpIdList() {
        return gdpIdList;
    }

    public void setGdpIdList(List<FilterDto> gdpIdList) {
        this.gdpIdList = gdpIdList;
    }

    public List<FilterDto> getBusinessUnitList() {
        return businessUnitList;
    }

    public void setBusinessUnitList(List<FilterDto> businessUnitList) {
        this.businessUnitList = businessUnitList;
    }

    public List<FilterDto> getPsProjectIdList() {
        return psProjectIdList;
    }

    public void setPsProjectIdList(List<FilterDto> psProjectIdList) {
        this.psProjectIdList = psProjectIdList;
    }

    public List<FilterDto> getGddList() {
        return gddList;
    }

    public void setGddList(List<FilterDto> gddList) {
        this.gddList = gddList;
    }

    public List<FilterDto> getProgramManager() {
        return programManager;
    }

    public void setProgramManager(List<FilterDto> programManager) {
        this.programManager = programManager;
    }

}
