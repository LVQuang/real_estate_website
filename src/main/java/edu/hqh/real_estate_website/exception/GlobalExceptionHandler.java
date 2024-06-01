package edu.hqh.real_estate_website.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handlinMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                .body((Objects.requireNonNull(exception.getFieldError()).getDefaultMessage()));
    }
}
