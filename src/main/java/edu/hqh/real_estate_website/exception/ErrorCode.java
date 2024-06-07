package edu.hqh.real_estate_website.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTS(1001, "User exists"),
    USER_NOT_EXISTS(1002, "User doesn't exist"),
    PASSWORD_INVALID(1003, "Password at least 5 characters"),
    UNCATEGORIZED_ERROR(9999, "Uncategorized error")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

}
