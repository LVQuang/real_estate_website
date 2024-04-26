package edu.hqh.real_estate_website.dto.response;

import edu.hqh.real_estate_website.enums.PostState;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostListingResponse {
    String title;
    String type;
    String address;
    Double price;
    LocalDate postDate;
    PostState available;
}
