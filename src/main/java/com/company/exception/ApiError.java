package com.company.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message, LocalDateTime timestamp) {
        super();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

}
