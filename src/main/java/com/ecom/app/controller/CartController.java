package com.ecom.app.controller;


import com.ecom.app.dto.CartItemRequest;
import com.ecom.app.model.Product;
import com.ecom.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        boolean ok = cartService.addToCart(userId, request);
        if (!ok) {
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId)
    {
      boolean deleted=  cartService.deleteItemFromCart(userId,productId);
      return deleted ? ResponseEntity.noContent().build()
              : ResponseEntity.notFound().build();
    }
    @GetMapping
    public List<Product> getAllCartProducts(
            @RequestHeader("X-User-ID") String userId
    )
    {
        return cartService.getAllProductsFromCart(userId);
    }
}
