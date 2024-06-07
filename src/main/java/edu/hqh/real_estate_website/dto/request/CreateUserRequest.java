package edu.hqh.real_estate_website.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    String name;
    @Size(min = 5, message = "PASSWORD_INVALID")
    String password;
    String email;
    String phone;
    String userGender;
    LocalDate dob;
}
