package com.mghr4937.demo.web.controller.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(status).build();

        return new ResponseEntity<>(apiError, headers, status);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getLocalizedMessage());
        ErrorResponse apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(status).build();

        return new ResponseEntity<>(apiError, headers, status);
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getLocalizedMessage());
        ErrorResponse apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleInvalidArgument(RuntimeException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getLocalizedMessage());
        ErrorResponse apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<Object> handleDateTimeParseException(
            DateTimeParseException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getLocalizedMessage());
        ErrorResponse apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ErrorResponse apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


}
