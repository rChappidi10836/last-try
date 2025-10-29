package com.gdp.backend.dto;

public class CslNameDto {

    private Integer cslId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String cslFullName;

    public String getCslFullName() {
        return cslFullName;
    }

    public void setCslFullName(String cslFullName) {
        this.cslFullName = cslFullName;
    }

    public Integer getCslId() {
        return cslId;
    }

    public void setCslId(Integer cslId) {
        this.cslId = cslId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
