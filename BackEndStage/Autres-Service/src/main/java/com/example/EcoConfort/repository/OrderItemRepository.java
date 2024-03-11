package com.example.EcoConfort.repository;

import com.example.EcoConfort.entity.Order;
import com.example.EcoConfort.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
    List<OrderItem> findByOrder(Order order);
}
