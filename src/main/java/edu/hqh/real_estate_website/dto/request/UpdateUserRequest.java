package edu.hqh.real_estate_website.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String name;
    @Size(min = 5, message = "PASSWORD_INVALID")
    String password;
    String phone;
    String userGender;
}
