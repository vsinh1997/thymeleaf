package com.example.thymeleaftutorial.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handlerNotFoundException(NotFoundException exception, WebRequest request) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
