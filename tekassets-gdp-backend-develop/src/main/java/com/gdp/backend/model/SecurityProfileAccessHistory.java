package com.gdp.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SecurityProfileAccessHistory extends AccessHistory {

    @Column(name = "Question")
    private String securityQuestion;

    @Column(name = "Answer")
    private String securityAnswerUpdatedFrom;

    @Column(name = "UpdatedAnswer")
    private String securityAnswerUpdatedTo;

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
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
