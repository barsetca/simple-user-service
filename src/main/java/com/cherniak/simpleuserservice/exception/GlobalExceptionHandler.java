package com.cherniak.simpleuserservice.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ProblemDetail handleValueExists(AlreadyExistsException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Already exists value exception");
        detail.setDetail(ex.getMessage());
        return detail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageReadableEx(HttpMessageNotReadableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodValidationEx(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Method argument not valid error");
        detail.setDetail("Invalid fields");
        detail.setProperty("errors",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(this::getErrorFieldMap)
                        .toList());
        return detail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationEx(ConstraintViolationException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Constraint modelfield value error");
        detail.setDetail("Invalid model value ");
        detail.setProperty("errors",
                ex.getConstraintViolations().stream()
                        .map(this::getConstraintErrorFieldMap)
                        .toList()
        );
        return detail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundEx(NotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private Map<String, String> getErrorFieldMap(FieldError error) {
        return Map.of(
                "field", error.getField(),
                "code", error.getCode(),
                "message", error.getDefaultMessage()
        );
    }

    private Map<String, String> getConstraintErrorFieldMap(ConstraintViolation<?> error) {
        return Map.of(
                "field", error.getPropertyPath().toString(),
                "message", error.getMessage()
        );
    }
}
