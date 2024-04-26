package edu.hqh.real_estate_website.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentResponse {
    String id;
    String address;
    String description;
    Double price;

}
