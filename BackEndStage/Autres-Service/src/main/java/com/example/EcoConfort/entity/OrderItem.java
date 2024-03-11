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
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Constructeurs, getters, setters, etc.
}
