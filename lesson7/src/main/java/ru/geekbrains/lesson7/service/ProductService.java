package ru.geekbrains.lesson7.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.geekbrains.lesson7.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    Page<Product> getAllProductsWithinPriceRangeByPage(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    List<Product> getAllProductsWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    void addProduct(Product product);

    void deleteProduct(Long id);

    void editProduct(Long id, Product product);

    Product joinFullCharacteristicListToProduct(Product product);
}
