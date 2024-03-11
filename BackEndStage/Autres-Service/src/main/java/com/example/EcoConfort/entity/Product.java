package com.example.EcoConfort.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String brand;
    private String description;
    private double price;
//    private String image;

@Lob
private byte[] image; // Pour stocker l'image directement dans la base de données

    private int stockQuantity;
    private boolean inStock;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PostPersist
    public void updateStockStatus() {
        this.inStock = this.stockQuantity > 0;
    }


}
