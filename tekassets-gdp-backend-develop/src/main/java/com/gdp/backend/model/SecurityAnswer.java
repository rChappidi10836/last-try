package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class SecurityAnswer extends Base {

    @Column(name = "Answer")
    private String answer;

    @Column(name = "MandatoryComment")
    private Boolean isMandatoryComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SecurityQuestionId")
    @JsonBackReference
    private SecurityQuestions securityQuestions;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "securityAnswer")
    private ProjectSecurityProfile projectSecurityProfile;



    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getMandatoryComment() {
        return isMandatoryComment;
    }

    public void setMandatoryComment(Boolean mandatoryComment) {
        isMandatoryComment = mandatoryComment;
    }

    public SecurityQuestions getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(SecurityQuestions securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public ProjectSecurityProfile getProjectSecurityProfile() {
        return projectSecurityProfile;
    }

    public void setProjectSecurityProfile(ProjectSecurityProfile projectSecurityProfile) {
        this.projectSecurityProfile = projectSecurityProfile;
    }


}
