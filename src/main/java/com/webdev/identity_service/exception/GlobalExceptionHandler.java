package com.webdev.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //gom cac exception ve 1 cho
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class) //bat exception runtime
    ResponseEntity<String> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage()); // tra ve bad request
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class) //bat valid exception
    ResponseEntity<String> handlingValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(exception.getFieldError().getDefaultMessage()); // tra ve bad request
    }
}
