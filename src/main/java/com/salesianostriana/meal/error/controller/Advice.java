package com.salesianostriana.meal.error.controller;
import com.salesianostriana.meal.error.exception.InvalidSearchException;
import com.salesianostriana.meal.error.model.Error;
import com.salesianostriana.meal.error.model.SubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidSearchException.class)
    public ResponseEntity<?> handleInvalidSearch(InvalidSearchException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Error.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpStatus.NOT_FOUND.value())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .message(exception.getMessage())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                        .subErrors(ex.getAllErrors().stream().map(SubError::fromObjectError).toList())
                .build());
    }
}
