package edu.hqh.real_estate_website.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddPostRequest {
    @NonNull
    String id;
    @NonNull
    String title;
    @NonNull
    String address;
    @NonNull
    String description;
    @NonNull
    Double price;
    @NonNull
    String postDate;
    @NonNull
    String type;
}
