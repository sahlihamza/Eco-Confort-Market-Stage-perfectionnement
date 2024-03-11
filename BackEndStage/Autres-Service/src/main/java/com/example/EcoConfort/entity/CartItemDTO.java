package com.example.EcoConfort.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItemDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Product product; // Inclure l'objet Product complet
    private int quantity;
    // Ajouter d'autres champs pertinents, comme le prix, etc.

    // Constructeurs, getters et setters
}