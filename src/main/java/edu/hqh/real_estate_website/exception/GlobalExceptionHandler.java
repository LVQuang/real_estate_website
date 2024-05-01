package edu.hqh.real_estate_website.exception;

import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handlingOtherException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/otherException");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
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

    @ExceptionHandler(value = WebException.class)
    public ModelAndView handlingWebException(WebException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("errorCode", errorCode);
        return modelAndView;
    }
}
