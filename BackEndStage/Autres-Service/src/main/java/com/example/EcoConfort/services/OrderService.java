package com.example.EcoConfort.services;

import com.example.EcoConfort.entity.*;
import com.example.EcoConfort.repository.CartRepository;
import com.example.EcoConfort.repository.OrderItemRepository;
import com.example.EcoConfort.repository.OrderRepository;
import com.example.EcoConfort.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

//    @Transactional
//    public Order createOrderFromCart(Long cartId, Order orderDetails) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        // Vous pouvez ajouter des vérifications supplémentaires ici, par exemple, s'assurer que le panier n'est pas vide.
//
//        Order order = new Order();
//        order.setFirstName(orderDetails.getFirstName());
//        order.setLastName(orderDetails.getLastName());
//        order.setPhone(orderDetails.getPhone());
//        order.setEmail(orderDetails.getEmail());
//        order.setAddress(orderDetails.getAddress());
//        order.setCity(orderDetails.getCity());
//        order.setPostalCode(orderDetails.getPostalCode());
//        order.setMessageToSeller(orderDetails.getMessageToSeller());
//
//        double totalPrice = 0;
//
//        for (CartItem cartItem : cart.getItems()) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setCartItem(cartItem);
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setOrder(order);
//            // Ici, vous pouvez également calculer le prix total.
//            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
//
//            order.getOrderItems().add(orderItem);
//        }
//
//        order.setTotalPrice(totalPrice);
//        Order savedOrder = orderRepository.save(order);
//
//        // Supprimez ou mettez à jour le panier selon votre logique d'application.
//
//        return savedOrder;
//    }

    @Transactional
    public Order createOrderFromCart(Long cartId, Order orderDetails) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setFirstName(orderDetails.getFirstName());
        order.setLastName(orderDetails.getLastName());
        order.setPhone(orderDetails.getPhone());
        order.setEmail(orderDetails.getEmail());
        order.setAddress(orderDetails.getAddress());
        order.setCity(orderDetails.getCity());
        order.setPostalCode(orderDetails.getPostalCode());
        order.setMessageToSeller(orderDetails.getMessageToSeller());
        order.setStatus(true);


        double totalPrice = 0;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // Vérifiez si le stock est suffisant
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getProductName());
            }

            // Décrémentez le stock du produit
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setCartItem(cartItem);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);

            totalPrice += product.getPrice() * cartItem.getQuantity();
            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);

        // Optionnel: Vider le panier après la création de la commande
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }





        // Convertir OrderItem en OrderItemDTO
        private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
            return new OrderItemDTO(
                    orderItem.getId(),
                    orderItem.getCartItem().getId(), // Supposer que chaque OrderItem a un Product
                    orderItem.getQuantity()
            );
        }

        // Convertir Order en OrderDTO (incluant ses OrderItems)
        public OrderDTO convertToOrderDTO(Order order) {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setFirstName(order.getFirstName());
            dto.setLastName(order.getLastName());
            dto.setPhone(order.getPhone());
            dto.setEmail(order.getEmail());
            dto.setAddress(order.getAddress());
            dto.setCity(order.getCity());
            dto.setPostalCode(order.getPostalCode());
            dto.setMessageToSeller(order.getMessageToSeller());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setStatus(order.isStatus());

            // Assumant que la conversion d'OrderItem en OrderItemDTO est correctement implémentée
            List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                    .map(this::convertToOrderItemDTO)
                    .collect(Collectors.toList());
            dto.setOrderItems(orderItemDTOs);

            return dto;
        }


    // Récupérer toutes les commandes avec leurs éléments en tant que DTOs
        public List<OrderDTO> getAllOrdersWithItemsDto() {
            return orderRepository.findAll().stream()
                    .map(this::convertToOrderDTO)
                    .collect(Collectors.toList());
        }

    @Transactional
    public Order toggleOrderStatus(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Bascule le statut actuel de la commande
        order.setStatus(!order.isStatus());
        return orderRepository.save(order);
    }
    public long countOpenOrders() {
        // Compte et retourne le nombre de commandes avec le statut 'true' (ouvertes)
        return orderRepository.countByStatus(true);
    }

    public Double calculateTotalRevenueFromClosedOrders() {
        List<Order> closedOrders = orderRepository.findByStatus(false);
        double totalRevenue = closedOrders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
        return totalRevenue;
    }









    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderItemRepository.findByOrder(order);
    }
}

