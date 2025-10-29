package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class ProjectSecurityProfile extends Base {

    @Column(name = "Selected")
    private Boolean selected;

    @Column(name = "Comments")
    private String comments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SecurityAnswerId")
    private SecurityAnswer securityAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    @JsonBackReference
    private Project project;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public SecurityAnswer getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(SecurityAnswer securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


}
