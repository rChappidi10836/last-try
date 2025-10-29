package com.gdp.backend.dto;

import java.util.List;

public class SecurityProfileDto {

    private Boolean isMultiselect;

    private Integer position;

    private String securityQuestion;

    private String comments;

    private List<SecurityProfileAnswerDto> securityAnswers;

    public Boolean getMultiselect() {
        return isMultiselect;
    }

    public void setMultiselect(Boolean multiselect) {
        isMultiselect = multiselect;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<SecurityProfileAnswerDto> getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(List<SecurityProfileAnswerDto> securityAnswers) {
        this.securityAnswers = securityAnswers;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
