package com.example.EcoConfort.controllers;

import com.example.EcoConfort.entity.CartItem;
import com.example.EcoConfort.entity.CartItemDTO;
import com.example.EcoConfort.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

//    @PostMapping("/addToCart")
//    public ResponseEntity<?> addToCart(@RequestParam Long customerId,
//                                       @RequestParam Long productId,
//                                       @RequestParam int quantity) {
//        try {
//            cartService.addToCart(customerId, productId, quantity);
//            return ResponseEntity.ok().body("Product added to cart successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping("/addToCart/{customerId}/{productId}/{quantity}")
    public ResponseEntity<?> addToCart(@PathVariable Long customerId,
                                       @PathVariable Long productId,
                                       @PathVariable int quantity) {
        try {
            cartService.addToCart(customerId, productId, quantity);
            return ResponseEntity.ok().body("Product added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/countItems/{customerId}")
    public ResponseEntity<?> countItemsInCart(@PathVariable Long customerId) {
        try {
            int itemCount = cartService.countItemsInCart(customerId);
            return ResponseEntity.ok(itemCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @GetMapping("/items/{customerId}")
//    public ResponseEntity<?> getCartItems(@PathVariable Long customerId) {
//        try {
//            List<CartItem> cartItems = cartService.getCartItems(customerId);
//            // Convertir en DTO si nécessaire
//            return ResponseEntity.ok(cartItems);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    @GetMapping("/items/{customerId}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Long customerId) {
        try {
            List<CartItemDTO> cartItemsDTO = cartService.getCartItemsAsDTOs(customerId);
            return ResponseEntity.ok(cartItemsDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @DeleteMapping("/items/{customerId}/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long customerId, @PathVariable Long itemId) {
        try {
            cartService.removeCartItem(customerId, itemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error removing item");
        }
    }

    @GetMapping("/getId/{cartId}")
    public ResponseEntity<Long> getCartIdById(@PathVariable Long cartId) {
        try {
            Long retrievedCartId = cartService.getCartIdById(cartId);
            return new ResponseEntity<>(retrievedCartId, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/clear/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        try {
            cartService.clearCart(customerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/updateQuantity/{customerId}/{itemId}/{quantity}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long customerId,
                                                    @PathVariable Long itemId,
                                                    @PathVariable int quantity) {
        try {
            cartService.updateCartItemQuantity(customerId, itemId, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("customerCartID/{userId}")
    public ResponseEntity<?> getCartIdByUserId(@PathVariable Long userId) {
        return cartService.getCartIdByUserId(userId)
                .map(cartId -> ResponseEntity.ok().body(cartId))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }





}