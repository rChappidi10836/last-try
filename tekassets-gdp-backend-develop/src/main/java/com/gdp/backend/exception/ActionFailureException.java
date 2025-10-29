package com.gdp.backend.exception;

import java.util.Arrays;
import java.util.List;

public class ActionFailureException extends Exception {

    private static final long serialVersionUID = 2019958418459951359L;

    private List<String> errorDetails;

    private List<String> errorData;

    public ActionFailureException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * @param errorMessage message
     * @param ex message
     */
    public ActionFailureException(String errorMessage, Exception ex) {
        super(errorMessage, ex);
    }

    /**
     * @param errorMessage message
     * @param errorDetails message
     */
    public ActionFailureException(String errorMessage, String... errorDetails) {
        super(errorMessage);
        this.errorDetails = Arrays.asList(errorDetails);
    }

    public ActionFailureException(String errorMessage, List<String> errorData) {
        super(errorMessage);
        this.errorData = errorData;
    }

    
    public List<String> getErrorDetails() {
        return errorDetails;
    }

    public List<String> getErrorData() {
        return errorData;
    }
}

