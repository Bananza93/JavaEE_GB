package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.dto.Product;
import ru.geekbrains.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    public void addNewProduct(Product product) {
        productRepository.addNewProduct(product);
    }
}
