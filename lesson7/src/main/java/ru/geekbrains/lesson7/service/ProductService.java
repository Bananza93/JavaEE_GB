package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProductsWithPriceRange(Float minPrice, Float maxPrice) {
        if (minPrice < Product.MIN_PRICE || minPrice > Product.MAX_PRICE) minPrice = Product.MIN_PRICE;
        if (maxPrice < Product.MIN_PRICE || maxPrice > Product.MAX_PRICE) maxPrice = Product.MAX_PRICE;
        if (maxPrice < minPrice) {
            float tmpMinPrice = minPrice;
            minPrice = maxPrice;
            maxPrice = tmpMinPrice;
        }
        return productRepository.findAllProductsWithinPriceRange(minPrice, maxPrice);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
