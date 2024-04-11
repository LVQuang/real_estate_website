package edu.hqh.real_estate_website.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sender;
    private Long receiver;

    // FK With Post many to one
    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    // FK With User many to many
    @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
    private List<UserContact> userContacts = new ArrayList<>();
}