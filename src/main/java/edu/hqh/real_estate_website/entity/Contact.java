package edu.hqh.real_estate_website.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String message;
    LocalDate contactDate = LocalDate.now();
}
