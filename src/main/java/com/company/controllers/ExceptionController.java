package com.company.controllers;

import com.company.exception.ApiError;
import com.company.exception.ResourceNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFound.class})
    public ResponseEntity<Object> handleNotFound(ResourceNotFound ex) {
        String errors = ex.toString();
        System.out.println("ExceptionHandler.handleNotFound");
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }
}
