package com.mghr4937.demo.web.controller.error.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private List<String> message;
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    private LocalDateTime timeStamp;

}



