package com.example.EcoConfort.repository;

import com.example.EcoConfort.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire

}