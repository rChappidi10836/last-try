package com.gdp.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtherTeamMemberDto {

    @JsonProperty("members")
    private String members;

    @JsonProperty("role")
    private Integer role;

    @JsonProperty("value")
    private AddResourceDto addResourceDto;


    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public AddResourceDto getAddResourceDto() {
        return addResourceDto;
    }

    public void setAddResourceDto(AddResourceDto addResourceDto) {
        this.addResourceDto = addResourceDto;
    }
}
