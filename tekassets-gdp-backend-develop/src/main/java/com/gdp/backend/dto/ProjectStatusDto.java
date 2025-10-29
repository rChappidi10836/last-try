package com.gdp.backend.dto;

import com.gdp.backend.enums.ProjectOverAllStatus;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.Objects;

import static com.gdp.backend.common.Utils.getStatusForFrontend;

public class ProjectStatusDto {

    private String gdpId;

    private Integer projectId;

    private String projectName;

    private String accountName;

    private String csl;

    private String statusDate;

    private String status;

    private String statusSummary;
    
    private String startDate;
    
	private String endDate;
    
    private String location;

    private String gdm;

    private String gdd;

    private String pm;

    private String practice;

    private String businessUnit;

    private String psId;

    private String currentPhase;

    private Integer opcoId;

    public Integer getOpcoId() {
        return opcoId;
    }

    public void setOpcoId(Integer opcoId) {
        this.opcoId = opcoId;
    }
    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    public String getGdd() {
        return gdd;
    }

    public void setGdd(String gdd) {
        this.gdd = gdd;
    }
    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectStatusDto)) return false;
        ProjectStatusDto that = (ProjectStatusDto) o;
        return projectId.equals(that.projectId) &&
                projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gdpId, projectId, projectName);
    }

    public ProjectStatusDto() {
    }

    public ProjectStatusDto(Object[] columns, String csl, Integer pageNumber, Integer pageSize) {
        this.gdpId = (String) columns[7];
        this.projectId = (Integer) columns[1];
        this.projectName = (String) columns[2];
        this.accountName = (String) columns[3];
        this.location = (String) columns[12];
        this.gdm = (String) columns[13];
        this.pm = (columns.length > 18) ? (String) columns[18] : null;
//        this.gdd = (columns.length > 19) ? (String) columns[19] : null;
        this.opcoId = Integer.parseInt(columns[11].toString()) ;
        if(columns[14]!=null) this.practice = (String)columns[14];
        if(columns[15]!=null) this.businessUnit = (String)columns[15];
        if(columns[16]!=null) this.currentPhase = (String)columns[16];
        if(columns[17]!=null) this.psId = (String)columns[17];
        if(OpportunityDto.getFormattedDate((Date) columns[9])!=null) {
        	this.startDate = OpportunityDto.getFormattedDate((Date) columns[9]);
        }else {
        	this.startDate ="";
        }
        if(OpportunityDto.getFormattedDate((Date) columns[10])!=null) {
        	this.endDate = OpportunityDto.getFormattedDate((Date) columns[10]);
        }else {
        	this.endDate ="";
        }
        this.csl = csl;
        if (Boolean.TRUE.equals(columns[8])) {
            this.statusDate = null;
            this.status = null;
            this.statusSummary = null;
        } else {
            this.statusDate = OpportunityDto.getFormattedDate((Date) columns[4]);
            if (pageNumber == 0 && pageSize == 0) {
                this.status = getStatusForExcel((String) columns[5]);
            } else {
                this.status = getStatusForFrontend((String) columns[5]);
            }
            this.statusSummary = (String) columns[6];
        }
    }

    private String getStatusForExcel(String statusFromDatabase) {
        String statusForExcel = null;
        if (("Red").equals(statusFromDatabase)) {
            statusForExcel = ProjectOverAllStatus.RED.getStatus();
        } else if (("Yellow").equals(statusFromDatabase)) {
            statusForExcel = ProjectOverAllStatus.YELLOW.getStatus();
        } else if (("Green").equals(statusFromDatabase)) {
            statusForExcel = ProjectOverAllStatus.GREEN.getStatus();
        }
        return statusForExcel;
    }

    public String getGdpId() {
        return gdpId;
    }

    public void setGdpId(String gdpId) {
        this.gdpId = gdpId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCsl() {
        return csl;
    }

    public void setCsl(String csl) {
        this.csl = csl;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = OpportunityDto.getFormattedDate(statusDate);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusSummary() {
        return statusSummary;
    }

    public void setStatusSummary(String statusSummary) {
        this.statusSummary = statusSummary;
    }

    public String getGdm() {
        return gdm;
    }

    public void setGdm(String gdm) {
        this.gdm = gdm;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }
}
