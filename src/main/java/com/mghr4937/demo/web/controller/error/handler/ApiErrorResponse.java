package com.mghr4937.demo.web.controller.error.handler;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {

    //http status code
    private HttpStatus status;
    // in case we want to provide API based custom error code
    private String error_code;
    // customer error message to the client API
    private String message;
    // Any furthur details which can help client API
    private String detail;
    // Time of the error.
    private LocalDateTime timeStamp;

}
