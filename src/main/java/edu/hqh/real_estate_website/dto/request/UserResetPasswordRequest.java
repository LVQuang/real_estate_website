package edu.hqh.real_estate_website.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResetPasswordRequest {
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    String re_password;
}
