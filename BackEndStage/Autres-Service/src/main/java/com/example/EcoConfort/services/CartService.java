package com.example.EcoConfort.services;

import com.example.EcoConfort.entity.*;
import com.example.EcoConfort.repository.CartItemRepository;
import com.example.EcoConfort.repository.CartRepository;
import com.example.EcoConfort.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void addToCart(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElse(new Cart(null, customerId, new ArrayList<>()));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem(null, product, cart,quantity);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        if (cart.getId() == null) {
            cartRepository.save(cart);
        }
    }

    public int countItemsInCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElse(new Cart(null, customerId, new ArrayList<>()));

        return cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public List<CartItem> getCartItems(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer with ID: " + customerId));

        return new ArrayList<>(cart.getItems());
    }

//    public CartItemDTO convertToDto(CartItem cartItem) {
//        CartItemDTO dto = new CartItemDTO();
//        dto.setId(cartItem.getId());
//        dto.setProductId(cartItem.getProduct().getId());
//        dto.setQuantity(cartItem.getQuantity());
//        // Configurer d'autres champs si nécessaire
//        return dto;
//    }

    public CartItemDTO convertToDto(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getProduct(), // Inclure l'objet Product complet
                cartItem.getQuantity()
        );
    }


    public List<CartItemDTO> getCartItemsAsDTOs(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer with ID: " + customerId));

        List<CartItem> cartItems = new ArrayList<>(cart.getItems());
        return cartItems.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void removeCartItem(Long customerId, Long itemId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        // Vous pouvez également mettre à jour le cart si nécessaire
        cartRepository.save(cart);
    }
    public Long getCartIdById(Long cartId) {
        return cartRepository.findById(cartId)
                .map(Cart::getId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
    }

    @Transactional
    public void clearCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCartItemQuantity(Long customerId, Long itemId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        // Vous pouvez également mettre à jour le cart si nécessaire
        cartRepository.save(cart);
    }
    public Optional<Long> getCartIdByUserId(Long userId) {
        return cartRepository.findByCustomerId(userId)
                .map(Cart::getId);
    }











}
