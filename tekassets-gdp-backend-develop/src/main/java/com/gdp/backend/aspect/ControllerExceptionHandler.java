package com.gdp.backend.aspect;

import com.gdp.backend.common.Constants;
import com.gdp.backend.domain.Response;
import com.gdp.backend.exception.ActionFailureException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    @Qualifier("logger")
    Logger logger;

    @Autowired
    private Response<?> response;

    @ExceptionHandler(ActionFailureException.class)
    public ResponseEntity<Response<?>> handleException(ActionFailureException ex) {
        logger.error(ex.getMessage(), ex);
        response.setError(new Response.Error().setException(ex.toString()).setMessage(ex.getMessage())
                .setErrors(Arrays.asList(ex.getErrorData().toArray()))
                .setStatus(HttpStatus.BAD_REQUEST.value()));
        response.setStatusCode(ex.getMessage());
        return new ResponseEntity<Response<?>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Response<?>> handleException(AccessDeniedException ex){
        logger.error(ex.getMessage(), ex);
        response.setError(new Response.Error().setException(ex.toString()).setMessage("ACTION FORBIDDEN FOR USER")
                .setStatus(HttpStatus.FORBIDDEN.value()));
        response.setStatusCode(HttpStatus.FORBIDDEN.toString());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        response.setError(new Response.Error().setException(ex.toString()).setMessage("UNABLE_TO_PROCESS_REQUEST")
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setStatusCode(Constants.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<Response<?>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

