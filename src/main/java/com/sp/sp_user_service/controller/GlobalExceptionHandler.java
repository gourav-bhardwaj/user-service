package com.sp.sp_user_service.controller;

import com.sp.sp_user_service.model.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        logger.warn("Validation failed: {}", errorMessage);
        return ResponseEntity.badRequest()
                .body(new GenericResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void>> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred"));
    }
}
