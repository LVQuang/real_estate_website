package edu.hqh.real_estate_website.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Lob
    Blob content;
    LocalDate imageDate;
    @ManyToOne
    Post post;
}
