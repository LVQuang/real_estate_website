package edu.hqh.real_estate_website.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum RoleName {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    String name;
}
