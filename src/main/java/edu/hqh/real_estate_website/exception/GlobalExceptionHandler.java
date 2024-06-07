package edu.hqh.real_estate_website.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Console;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handlingExceptionException() {
        var exception = new AppException(ErrorCode.UNCATEGORIZED_ERROR);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<String> handlingAppException(AppException exception) {
        return ResponseEntity.badRequest().body(exception.getErrorCode().getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        var errorCode = ErrorCode.valueOf(enumKey);

        return ResponseEntity.badRequest()
                .body(errorCode.getMessage());
    }
}
