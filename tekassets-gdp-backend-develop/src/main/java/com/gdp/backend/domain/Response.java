package com.gdp.backend.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Response<T> {

    private List<T> data;
    private String successMessage;
    private Error error;
    private String statusCode;

    private String opcoid;

    // builder class for error response
    public static class Error {

        private String exception;
        private String message;
        private Integer status;
        private List<Object> errors;

        public String getException() {
            return exception;
        }

        public Error setException(String exception) {
            this.exception = exception;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Error setMessage(String message) {
            this.message = message;
            return this;
        }

        public Integer getStatus() {
            return status;
        }

        public Error setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public List<Object> getErrors() {
            return errors;
        }

        public Error setErrors(List<Object> errors) {
            this.errors = errors;
            return this;
        }

    }

    public List<T> getData() {
        return data;
    }

    public Response<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    public Error getError() {
        return error;
    }

    public Response<T> setError(Error error) {
        this.error = error;
        return this;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Response<T> setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getOpcoid() {
        return opcoid;
    }

    public void setOpcoid(String opcoid) {
        this.opcoid = opcoid;
    }

}

