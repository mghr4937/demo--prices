package com.mghr4937.demo.web.controller.error.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
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
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
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
        log.info("MethodArgumentNotValidException :: " + ex.getMessage());


        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        var errorResponse = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(status).build();

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object>
    handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("HttpMessageNotReadableException :: " + ex.getMessage());


        var errors = Collections.singletonList(ex.getMostSpecificCause().getMessage());
        var apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(status).build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    @Override
    protected ResponseEntity<Object>
    handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("TypeMismatchException :: " + ex.getMessage());

        var errors = Collections.singletonList(ex.getMostSpecificCause().getMessage());
        var apiError = ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(status).build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return ErrorResponse.builder()
                .message(Collections.singletonList("Resource not found"))
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND).build();

    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleInvalidArgument(RuntimeException ex, WebRequest request) {
        log.info("IllegalArgumentException :: " + ex.getMessage());
        var errors = Collections.singletonList(ex.getMessage());
        return ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();

    }

    @ExceptionHandler({JsonProcessingException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJsonProcessingException(
            JsonProcessingException ex, WebRequest request) {
        log.error("JsonProcessingException :: " + ex.getMessage());
        var errors = Collections.singletonList(ex.getLocalizedMessage());
        return ErrorResponse.builder()
                .message(errors)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        log.info("ConstraintViolationException:: " + ex.getMessage());
        var errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        return ErrorResponse.builder()
                .message(errors)
                .status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(value = {ServerErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleServerErrorException(ServerErrorException ex, WebRequest request) {
        log.info("Server Error :: ", ex.getMessage());
        var errors = Collections.singletonList("Oops, something really bad happened");
        return ErrorResponse
                .builder()
                .message(errors)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timeStamp(LocalDateTime.now())
                .build();

    }


}
