package com.gdp.backend.dto;

public class SecurityProfileFormDataAnswerDto {

    private Integer securityAnswerId;

    private String comments;

    private Boolean selected;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Integer getSecurityAnswerId() {
        return securityAnswerId;
    }

    public void setSecurityAnswerId(Integer securityAnswerId) {
        this.securityAnswerId = securityAnswerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
