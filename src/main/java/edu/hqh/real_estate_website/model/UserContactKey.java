package edu.hqh.real_estate_website.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class UserContactKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "contact_id")
    private Long contactId;
}
