package ru.geekbrains.lesson9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson9.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
    List<Product> findAllProductsWithinPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
