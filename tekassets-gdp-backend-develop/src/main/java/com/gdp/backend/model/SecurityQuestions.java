package com.gdp.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class SecurityQuestions extends Base {

    @Column(name = "Question")
    private String question;

    @Column(name = "Multiselect")
    private Boolean multiSelect;

    @Column(name = "Position")
    private int position;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securityQuestions", cascade = CascadeType.ALL)
    private List<SecurityAnswer> securityAnswerList;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(Boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public List<SecurityAnswer> getSecurityAnswerList() {
        return securityAnswerList;
    }

    public void setSecurityAnswerList(List<SecurityAnswer> securityAnswerList) {
        this.securityAnswerList = securityAnswerList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
