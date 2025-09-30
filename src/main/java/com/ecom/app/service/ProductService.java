package com.ecom.app.service;

import com.ecom.app.dto.ProductRequest;
import com.ecom.app.model.Product;
import com.ecom.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product= new Product();
        updateProductFromRequest(product,productRequest);
        productRepository.save(product);
    }
}
