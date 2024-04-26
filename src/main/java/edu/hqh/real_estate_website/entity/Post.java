package edu.hqh.real_estate_website.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String title;
    LocalDate postDate;
    boolean available;
    String type;
    @OneToOne(mappedBy = "post")
    Content content;
    @ManyToOne
    User user;
}