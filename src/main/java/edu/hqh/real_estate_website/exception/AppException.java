package edu.hqh.real_estate_website.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

}
