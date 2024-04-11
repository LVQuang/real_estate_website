package edu.hqh.real_estate_website.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.util.Date;

public class UserTransaction {
    @EmbeddedId
    private UserTransactionKey id;

    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @ManyToOne
    private User user;

    @JoinColumn(name = "transaction_id")
    @MapsId("transactionId")
    @ManyToOne
    private Transaction transaction;
    private Date createdAt;

    public UserTransaction(User user, Transaction transaction, Date createdAt) {
        this.user = user;
        this.transaction = transaction;
        this.createdAt = createdAt;
        this.id = new UserTransactionKey(user.getId(),transaction.getId());
    }
}
