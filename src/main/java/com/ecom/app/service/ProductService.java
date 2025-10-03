package com.ecom.app.service;

import com.ecom.app.dto.ProductRequest;
import com.ecom.app.dto.ProductResponse;
import com.ecom.app.model.Product;
import com.ecom.app.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product= new Product();
        updateProductFromRequest(product,productRequest);
       Product savedProduct= productRepository.save(product);
       return mapToProductResponse(savedProduct);
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
//        product.setActive(productRequest.getActive());
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response= new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setPrice(savedProduct.getPrice());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setPrice(savedProduct.getPrice());
        response.setActive(savedProduct.getActive());
        return response;
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse deleteproductbyId(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return mapToProductResponse(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream().map(
                this::mapToProductResponse).collect(
                        Collectors.toList()
        );
    }
}
