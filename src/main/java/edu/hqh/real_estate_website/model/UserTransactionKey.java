package edu.hqh.real_estate_website.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserTransactionKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "transaction_id")
    private Long transactionId;
}
