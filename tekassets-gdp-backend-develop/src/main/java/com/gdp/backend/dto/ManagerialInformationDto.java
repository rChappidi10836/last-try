package com.gdp.backend.dto;

public class ManagerialInformationDto {

    private AddResourceDto selectedGlobalDeliveryManager;

    private AddResourceDto selectedNationalAccountOwner;

    private AddResourceDto selectedCustomerSuccessLead;

    private AddResourceDto selectedOsgPoa;

    private AddResourceDto selectedSolutionEngineer;

    private AddResourceDto selectedOsgBoa;

    private AddResourceDto selectedBdm;

    private AddResourceDto[] selectedTeamMembers;

    public AddResourceDto getSelectedGlobalDeliveryManager() {
        return selectedGlobalDeliveryManager;
    }

    public void setSelectedGlobalDeliveryManager(AddResourceDto selectedGlobalDeliveryManager) {
        this.selectedGlobalDeliveryManager = selectedGlobalDeliveryManager;
    }

    public AddResourceDto getSelectedNationalAccountOwner() {
        return selectedNationalAccountOwner;
    }

    public void setSelectedNationalAccountOwner(AddResourceDto selectedNationalAccountOwner) {
        this.selectedNationalAccountOwner = selectedNationalAccountOwner;
    }

    public AddResourceDto getSelectedCustomerSuccessLead() {
        return selectedCustomerSuccessLead;
    }

    public void setSelectedCustomerSuccessLead(AddResourceDto selectedCustomerSuccessLead) {
        this.selectedCustomerSuccessLead = selectedCustomerSuccessLead;
    }

    public AddResourceDto getSelectedOsgPoa() {
        return selectedOsgPoa;
    }

    public void setSelectedOsgPoa(AddResourceDto selectedOsgPoa) {
        this.selectedOsgPoa = selectedOsgPoa;
    }

    public AddResourceDto getSelectedSolutionEngineer() {
        return selectedSolutionEngineer;
    }

    public void setSelectedSolutionEngineer(AddResourceDto selectedSolutionEngineer) {
        this.selectedSolutionEngineer = selectedSolutionEngineer;
    }

    public AddResourceDto getSelectedOsgBoa() {
        return selectedOsgBoa;
    }

    public void setSelectedOsgBoa(AddResourceDto selectedOsgBoa) {
        this.selectedOsgBoa = selectedOsgBoa;
    }

    public AddResourceDto getSelectedBdm() {
        return selectedBdm;
    }

    public void setSelectedBdm(AddResourceDto selectedBdm) {
        this.selectedBdm = selectedBdm;
    }

    public AddResourceDto[] getSelectedTeamMembers() {
        return selectedTeamMembers;
    }

    public void setSelectedTeamMembers(AddResourceDto[] selectedTeamMembers) {
        this.selectedTeamMembers = selectedTeamMembers;
    }
}
