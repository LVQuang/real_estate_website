package edu.hqh.real_estate_website.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostUpdateRequest {
    String title;
    LocalDate postDate;
    boolean available;
    String type;
}
