package com.testShoopingCard.shopping_card.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String downloadURL;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
