package edu.hqh.real_estate_website.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UserContact {
    @EmbeddedId
    private UserContactKey id;

    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @ManyToOne
    private User user;

    @JoinColumn(name = "contact_id")
    @MapsId("contactId")
    @ManyToOne
    private Contact contact;

    private Date createdAt;

    public UserContact(User user, Contact contact, Date createdAt) {
        this.user = user;
        this.contact = contact;
        this.createdAt = createdAt;
        this.id = new UserContactKey(user.getId(),contact.getId());
    }
}
