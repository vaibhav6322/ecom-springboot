package com.ecom.app.repositories;

import com.ecom.app.dto.ProductResponse;
import com.ecom.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p FROM products p " +
            "WHERE p.active = true " +
            "AND p.stockQuantity > 0 " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product>searchProducts(String keyword);
}
