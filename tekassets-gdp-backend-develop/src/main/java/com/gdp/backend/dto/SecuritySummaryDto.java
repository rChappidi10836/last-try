package com.gdp.backend.dto;

public class SecuritySummaryDto {

    private String updatedSecurityQuestion;

    private String securityAnswerUpdatedFrom;

    private String securityAnswerUpdatedTo;

    public SecuritySummaryDto() {
    }

    public SecuritySummaryDto(String updatedSecurityQuestion, String securityAnswerUpdatedFrom, String securityAnswerUpdatedTo) {
        this.updatedSecurityQuestion = updatedSecurityQuestion;
        this.securityAnswerUpdatedFrom = securityAnswerUpdatedFrom;
        this.securityAnswerUpdatedTo = securityAnswerUpdatedTo;
    }

    public static SecuritySummaryDto mapToSecuritySummaryDto(Object[] columns) {
        return new SecuritySummaryDto((String) columns[0], (String) columns[1], (String) columns[2]);
    }

    public String getUpdatedSecurityQuestion() {
        return updatedSecurityQuestion;
    }

    public void setUpdatedSecurityQuestion(String updatedSecurityQuestion) {
        this.updatedSecurityQuestion = updatedSecurityQuestion;
    }

    public String getSecurityAnswerUpdatedFrom() {
        return securityAnswerUpdatedFrom;
    }

    public void setSecurityAnswerUpdatedFrom(String securityAnswerUpdatedFrom) {
        this.securityAnswerUpdatedFrom = securityAnswerUpdatedFrom;
    }

    public String getSecurityAnswerUpdatedTo() {
        return securityAnswerUpdatedTo;
    }

    public void setSecurityAnswerUpdatedTo(String securityAnswerUpdatedTo) {
        this.securityAnswerUpdatedTo = securityAnswerUpdatedTo;
    }
}
