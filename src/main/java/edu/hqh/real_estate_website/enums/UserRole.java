package edu.hqh.real_estate_website.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum UserRole {
    USER(0, "USER"),
    ADMIN(1, "ADMIN");

    UserRole(int code, String role) {
        this.code = code;
        this.role = role;
    }

    final int code;
    final String role;
}
