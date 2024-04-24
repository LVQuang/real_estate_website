package edu.hqh.real_estate_website.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date createdAt;
    private boolean available;
    private enum category { Rent, Sale }

    // FK With user many to one
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    // FK With Contact one to many
    @OneToMany(mappedBy="post", fetch = FetchType.LAZY)
    private List<Contact> contacts;

    // FK With Image one to many
    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private List<Image> images;


    // FK With Content one to one
    @JoinColumn(name = "content_fk_id",referencedColumnName = "content_id")
    @OneToOne
    private Content content;
}
