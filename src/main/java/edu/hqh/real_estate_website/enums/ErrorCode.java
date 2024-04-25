package edu.hqh.real_estate_website.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    ITEM_EXISTS(1001, "Item exists", HttpStatus.BAD_REQUEST),
    ITEM_DONT_EXISTS(1002, "Item don't exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006 , "Access Denied", HttpStatus.FORBIDDEN),
    OTHER_EXCEPTION(9999, "Other Exception", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatusCode statusCode;
}
