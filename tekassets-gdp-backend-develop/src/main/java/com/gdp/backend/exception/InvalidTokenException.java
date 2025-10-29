package com.gdp.backend.exception;

public class InvalidTokenException extends RuntimeException {
    private static final long serialVersionUID = 3404434161799702443L;

    public InvalidTokenException(String message, Throwable throwable) {
        super(message);
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
