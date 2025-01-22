package com.omega.jobportal.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String path;

    public ApiException(String message, HttpStatus httpStatus, String path) {
        super(message);
        this.httpStatus = httpStatus;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
