package com.company.controllers;

import com.company.exception.ApiError;
import com.company.exception.ResourceNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFound.class})
    public ResponseEntity<Object> handleNotFound(ResourceNotFound ex) {
        System.out.println("ExceptionHandler.handleNotFound");
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), LocalDateTime.now());
        return new ResponseEntity<>(err, new HttpHeaders(), err.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), LocalDateTime.now());
        return new ResponseEntity<>(err, new HttpHeaders(), err.getStatus());
    }
}
