package com.ecom.app.service;

import com.ecom.app.dto.CartItemRequest;
import com.ecom.app.model.CartItem;
import com.ecom.app.model.Product;
import com.ecom.app.model.User;
import com.ecom.app.repositories.CartItemRepository;
import com.ecom.app.repositories.ProductRepository;
import com.ecom.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

//    public boolean addToCart(String userId, CartItemRequest request) {
//       // Look for product
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//        if(productOpt.isEmpty())
//        {
//            return false;
//        }
//        Product product=productOpt.get();
//        if(product.getStockQuantity() < request.getQuantity())
//        {
//            return false;
//        }
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//
//        if(userOpt.isEmpty())
//        {
//            return false;
//        }
//        User user= userOpt.get();
//        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user,product);
//        if(existingCartItem !=null)
//        {
//            //update the quantity
//            existingCartItem.setQuantity((existingCartItem.getQuantity())+ request.getQuantity());
//            existingCartItem.setPrice(product.getPrice().multiply((BigDecimal.valueOf(existingCartItem.getQuantity()))));
//            cartItemRepository.save(existingCartItem);
//        }else {
//            //create new cart item
//            CartItem cartItem= new CartItem();
//            cartItem.setUser(user);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(request.getQuantity());
//            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
//            cartItemRepository.save(cartItem);
//        }
//        return true;
//    }
public boolean addToCart(String userId, CartItemRequest request) {
    // Look for product
    Optional<Product> productOpt = productRepository.findById(request.getProductId());
    if (productOpt.isEmpty())
        return false;

    Product product = productOpt.get();
    if (product.getStockQuantity() < request.getQuantity())
        return false;

    Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
    if (userOpt.isEmpty())
        return false;

    User user = userOpt.get();

    CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
    if (existingCartItem != null) {
        // Update the quantity
        existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
        existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
        cartItemRepository.save(existingCartItem);
    } else {
        // Create new cart item
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        cartItemRepository.save(cartItem);
    }
    return true;
}

}
