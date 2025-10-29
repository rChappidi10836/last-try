package com.gdp.backend.enums;

public enum EStatusCode {

    RECORD_ADDED_SUCCESSFULLY("Record added successfully"),
    RECORD_UPDATED_SUCCESSFULLY("Record updated successfully"),
    SUCCESS("Success"),

    ERROR_ON_ID("ID DOES NOT EXIST"),

    // FAILURE
    ERROR_ON_ADD("Exception occured while adding record: "),
    RECORD_NOT_FOUND("Exception occured while fetching data"),
    ERROR_ON_GET("Exception occured while getting data"),
    DUPLICATE_WEEK_ENDING_DATE("Status Report for this date already exists"),
    ERROR_WRITING_FILE("Exception occured while writing data"),
    ERROR_ON_INSTANTIATION("Utility Class instantiation exception"),
    ERROR_ON_DATE_PARSING("Date Parsing Exception"),
    ERROR_ON_SEND_MAIL("Mail Sending failure"),
    ERROR_ON_DETELE("Unable to delete due to existing references in projects");


    String message;

    EStatusCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
