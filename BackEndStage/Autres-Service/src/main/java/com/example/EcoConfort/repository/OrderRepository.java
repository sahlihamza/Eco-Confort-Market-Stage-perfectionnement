package com.example.EcoConfort.repository;

import com.example.EcoConfort.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByStatus(boolean status);
    List<Order> findByStatus(boolean status);


}
