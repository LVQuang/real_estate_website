package edu.hqh.real_estate_website.entity;

import edu.hqh.real_estate_website.enums.UserGender;
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
    String name;
    String password;
    @Column(nullable = false, unique = true)
    String email;
    String phone;
    UserGender userGender;
    @ManyToMany
    Set<Role> roles;
}