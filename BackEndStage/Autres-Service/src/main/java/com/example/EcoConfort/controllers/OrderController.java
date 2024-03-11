package com.example.EcoConfort.controllers;

import com.example.EcoConfort.entity.Order;
import com.example.EcoConfort.entity.OrderDTO;
import com.example.EcoConfort.entity.OrderItem;
import com.example.EcoConfort.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{cartId}")
    public ResponseEntity<Order> createOrderFromCart(@PathVariable Long cartId, @RequestBody Order orderDetails) {
        try {
            Order createdOrder = orderService.createOrderFromCart(cartId, orderDetails);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrdersWithItemsDto();
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            // Log the exception, e.g., logger.error("An error occurred", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/toggleStatus/{id}")
    public ResponseEntity<?> toggleOrderStatus(@PathVariable Long id) {
        try {
            Order updatedOrder = orderService.toggleOrderStatus(id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }



    @GetMapping("/items/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        try {
            List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/countOpen")
    public long countOpenOrders() {
        return orderService.countOpenOrders();
    }

    @GetMapping("/revenueClosed")
    public Double getTotalRevenueFromClosedOrders() {
        return orderService.calculateTotalRevenueFromClosedOrders();
    }
}
