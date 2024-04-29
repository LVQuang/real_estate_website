package edu.hqh.real_estate_website.exception;

import edu.hqh.real_estate_website.enums.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebException extends RuntimeException {
    ErrorCode errorCode;
}
