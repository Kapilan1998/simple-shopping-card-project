package com.testShoopingCard.shopping_card.Entity;

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
    private Integer id;
    private String fileName;
    private String fileType;
    @Lob        //  large object
    private Blob image;     // binary large objects
    private String downloadURL;
    private String fileSize;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
