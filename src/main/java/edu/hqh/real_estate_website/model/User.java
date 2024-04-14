package edu.hqh.real_estate_website.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private enum role { Admin, Client }

    // FK With Post one to many
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    // FK With Contact many to many
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserContact> userContacts;

    // FK With Transaction many to many
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserTransaction> userTransactions;

}