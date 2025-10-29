package com.gdp.backend.dto;

import java.util.Date;
import java.util.List;

public class SecurityProfileReportLog {

    private String lastUpdatedBy;

    private String dateAndTime;

    private List<SecuritySummaryDto> summary;

    public SecurityProfileReportLog() {
    }

    public SecurityProfileReportLog(String lastUpdatedBy, Date dateAndTime, List<SecuritySummaryDto> summary) {
        this.lastUpdatedBy = lastUpdatedBy;
        this.dateAndTime = ReportLogDto.getFormattedDateAndTime(dateAndTime);
        this.summary = summary;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = ReportLogDto.getFormattedDateAndTime(dateAndTime);
    }

    public List<SecuritySummaryDto> getSummary() {
        return summary;
    }

    public void setSummary(List<SecuritySummaryDto> summary) {
        this.summary = summary;
    }
}
