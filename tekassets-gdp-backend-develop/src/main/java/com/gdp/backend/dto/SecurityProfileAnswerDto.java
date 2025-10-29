package com.gdp.backend.dto;

public class SecurityProfileAnswerDto {

    private Integer securityAnswerId;

    private String securityAnswer;

    private Boolean isMandatory;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private Boolean selected;

    public SecurityProfileAnswerDto() {
    }

    public SecurityProfileAnswerDto(Integer securityAnswerId, String securityAnswer, Boolean isMandatory, Boolean selected) {
        this.securityAnswerId = securityAnswerId;
        this.securityAnswer = securityAnswer;
        this.isMandatory = isMandatory;
        this.selected = selected;
    }



    public static SecurityProfileAnswerDto mapToSecurityProfileAnswerDto(Object[] columns) {
        return new SecurityProfileAnswerDto((Integer) columns[4], (String) columns[3], (Boolean) columns[5], (Boolean) columns[6]);
    }

    public static SecurityProfileAnswerDto mapToSecurityProfileAnswerDtoAllAnswers(Object[] columns) {
        return new SecurityProfileAnswerDto((Integer) columns[3], (String) columns[2], (Boolean) columns[4], false);
    }

    public Integer getSecurityAnswerId() {
        return securityAnswerId;
    }

    public void setSecurityAnswerId(Integer securityAnswerId) {
        this.securityAnswerId = securityAnswerId;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }
}
