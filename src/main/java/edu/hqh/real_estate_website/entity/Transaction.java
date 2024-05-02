package edu.hqh.real_estate_website.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String sender;
    String receiver;
    LocalDate transactionDate;
    Double price;
    String state;
    @ManyToOne
    Post post;
}
