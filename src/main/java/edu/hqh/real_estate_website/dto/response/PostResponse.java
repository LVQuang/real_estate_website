package edu.hqh.real_estate_website.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String id;
    String title;
    LocalDate postDate;
    boolean available;
    String type;
    ContentResponse content;
    UserResponse user;
}
