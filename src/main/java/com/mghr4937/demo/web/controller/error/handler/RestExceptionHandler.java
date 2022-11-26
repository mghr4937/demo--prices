package com.mghr4937.demo.web.controller.error.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
                .ApiErrorResponseBuilder()
                .detail("Unprocessable Entity")
                .message("Not a valid input data")
                .error_code(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .timeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return handleExceptionInternal(ex, apiResponse,
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
                .ApiErrorResponseBuilder()
                .detail("Resource Not Found")
                .error_code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .status(HttpStatus.NOT_FOUND)
                .timeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return handleExceptionInternal(ex, apiResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {ServerErrorException.class})
    protected ResponseEntity<Object> handleServerErrorException(ServerErrorException ex, WebRequest request) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
                .ApiErrorResponseBuilder()
                .detail("Internal Server Error")
                .message("Oops, Something really bad happened")
                .error_code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return handleExceptionInternal(ex, apiResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}
