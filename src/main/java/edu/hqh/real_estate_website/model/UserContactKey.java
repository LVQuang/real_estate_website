package edu.hqh.real_estate_website.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserContactKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "contact_id")
    private Long contactId;
    public UserContactKey(Long userId, Long contactId){
        this.userId = userId;
        this.contactId = contactId;
    }
}
