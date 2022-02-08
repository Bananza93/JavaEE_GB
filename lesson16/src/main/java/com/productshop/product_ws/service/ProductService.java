package com.productshop.product_ws.service;

import com.productshop.product_ws.model.Product;
import com.productshop.product_ws.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> fillListWithProducts(double minPrice, double maxPrice) {
        return productRepository.getAllProductsByPriceRange(minPrice, maxPrice);
    }
}
