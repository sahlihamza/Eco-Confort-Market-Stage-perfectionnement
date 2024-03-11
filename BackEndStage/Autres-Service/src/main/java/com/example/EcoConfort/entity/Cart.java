package com.example.EcoConfort.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;  // ID du client

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items;



//    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
//    private Order order;



    // Total, etc.
}
