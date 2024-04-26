package edu.hqh.real_estate_website.exception;

import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<ErrorCode>> handlingOtherException(Exception exception) {
        log.error("Exception: ", exception);
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<ErrorCode>builder()
                        .result(ErrorCode.OTHER_EXCEPTION)
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<ErrorCode>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.<ErrorCode>builder()
                        .result(errorCode)
                        .build());
    }
}
