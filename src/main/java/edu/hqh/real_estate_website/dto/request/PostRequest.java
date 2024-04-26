package edu.hqh.real_estate_website.dto.request;

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
public class PostRequest {
    String title;
    String type;
    String address;
    String description;
    Double price;
    String available;
    Set<String> transactions;
}
