package com.gdp.backend.dto;

import java.util.Date;

public class AdminExportToExcelDto {

	String name;

	String previousName;

	String action;

	String modifiedBy;

	String createdBy;

	Date modifiedAt;

	public AdminExportToExcelDto(String name, String previousName, String action, String modifiedBy, String createdBy,
			Date modifiedAt) {
		super();
		this.name = name;
		this.previousName = previousName;
		this.action = action;
		this.modifiedBy = modifiedBy;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreviousName() {
		return previousName;
	}

	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public static AdminExportToExcelDto mapAdminExportToExcelDto(Object[] columns) {
		return new AdminExportToExcelDto((String) columns[0], (String) columns[1], (String) columns[2],
				(String) columns[3], (String) columns[4], (Date) columns[5]);
	}

}
