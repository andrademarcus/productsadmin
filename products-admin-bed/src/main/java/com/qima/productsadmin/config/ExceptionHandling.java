package com.qima.productsadmin.config;

import com.qima.productsadmin.exception.InvalidTokenException;
import com.qima.productsadmin.exception.RecordNotFoundException;
import com.qima.productsadmin.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse("An error occurred", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse("Record not found", e.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(errorResponse);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse("Token Exception", e.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse("Invalid username or password", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(AuthorizationDeniedException e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse("Access Denied", "You don't have access to this resource.");
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
