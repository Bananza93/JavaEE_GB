package ru.geekbrains.lesson7.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import ru.geekbrains.lesson7.model.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAllProductsBetweenPriceRange() {
        Page<Product> productList = productRepository
                .findAllProductsWithinPriceRangeByPage(BigDecimal.valueOf(200L), BigDecimal.valueOf(400L), null);
        assertTrue(productList.stream().allMatch(p -> p.getPrice().floatValue() >= 200 && p.getPrice().floatValue() <= 400));

        productList = productRepository
                .findAllProductsWithinPriceRangeByPage(BigDecimal.valueOf(0L), BigDecimal.valueOf(9999999L), null);
        assertTrue(productList.stream().allMatch(p -> p.getPrice().floatValue() >= 0 && p.getPrice().floatValue() <= 9999999));

        productList = productRepository
                .findAllProductsWithinPriceRangeByPage(BigDecimal.valueOf(0L), BigDecimal.valueOf(0), null);
        assertTrue(productList.isEmpty());
    }
}