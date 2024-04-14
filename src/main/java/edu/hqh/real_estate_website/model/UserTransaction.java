package edu.hqh.real_estate_website.model;

import jakarta.persistence.*;

import java.util.Date;
@Entity
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
