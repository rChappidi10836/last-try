package com.gdp.backend.dto;

import java.util.List;

public class SecurityProfileFormDataDto {

    private Integer projectId;

    List<SecurityProfileFormDataAnswerDto> securityAnswersList;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<SecurityProfileFormDataAnswerDto> getSecurityAnswersList() {
        return securityAnswersList;
    }

    public void setSecurityAnswersList(List<SecurityProfileFormDataAnswerDto> securityAnswersList) {
        this.securityAnswersList = securityAnswersList;
    }
}
