package ru.geekbrains.BackService.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.BackService.exception.ProductAlreadyExistsException;
import ru.geekbrains.BackService.model.Product;
import ru.geekbrains.BackService.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Transactional
    public void addProduct(Product product)  {
        try {
            productRepository.save(product);
        } catch (DataAccessException e) {
            throw new ProductAlreadyExistsException(product.getName());
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
