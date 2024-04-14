package edu.hqh.real_estate_website.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;
    private String description;
    private String address;
    private Double price;

    @OneToOne(mappedBy = "content")
    private Post post;

    public Content(String description, String address, Double price) {
        this.description = description;
        this.address = address;
        this.price = price;
    }
}
