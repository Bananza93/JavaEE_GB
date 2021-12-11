package ru.geekbrains.lesson4.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.lesson4.dto.Product;
import ru.geekbrains.lesson4.repository.ProductRepository;

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

    public void deleteProductById(int id) {
        productRepository.deleteProductById(id);
    }
}
