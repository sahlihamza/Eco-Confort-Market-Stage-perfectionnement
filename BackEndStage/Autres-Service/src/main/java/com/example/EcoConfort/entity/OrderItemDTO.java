package com.example.EcoConfort.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long cartItemId; // L'ID du CartItem associé
    private int quantity;
    // Vous pouvez ajouter d'autres champs nécessaires ici, par exemple, des détails sur le produit.
}
