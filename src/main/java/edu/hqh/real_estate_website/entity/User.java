package edu.hqh.real_estate_website.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true)
    String name;
    String password;
    String email;
    String phone;
    boolean isMale;
    @ManyToMany
    Set<Role> roles;
    @ManyToMany
    Set<Contact> contacts;
    @OneToMany(mappedBy = "user")
    Set<Transaction> transactions;
    @OneToMany(mappedBy = "user")
    Set<Post> posts;
}