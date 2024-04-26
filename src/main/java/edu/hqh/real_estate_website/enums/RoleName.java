package edu.hqh.real_estate_website.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RoleName {
    ADMIN("ADMIN"),
    USER("USER");
    String name;
}
