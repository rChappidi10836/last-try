package com.gdp.backend.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportLogDto {

    private String lastUpdatedBy;

    private String dateAndTime;

    public ReportLogDto() {
    }

    public ReportLogDto(Date dateAndTime, String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        this.dateAndTime = getFormattedDateAndTime(dateAndTime);
    }

    public static String getFormattedDateAndTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss aaa");
        return formatter.format(date);
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

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
