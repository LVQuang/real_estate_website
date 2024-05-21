package edu.hqh.real_estate_website.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum UserGender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    UserGender(String gender) {
        this.gender = gender;
    }

    final String gender;
}
