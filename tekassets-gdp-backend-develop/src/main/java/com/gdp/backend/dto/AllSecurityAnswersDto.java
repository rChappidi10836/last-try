package com.gdp.backend.dto;

public class AllSecurityAnswersDto {

    private Integer securityAnswerId;

    private String securityAnswer;

    private String securityQuestion;

    private Boolean isSelected;

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

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
