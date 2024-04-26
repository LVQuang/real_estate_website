package edu.hqh.real_estate_website.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Entity
@Getter @Service
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String description;
    String address;
    Double price;
    @OneToOne
    Post post;
}
