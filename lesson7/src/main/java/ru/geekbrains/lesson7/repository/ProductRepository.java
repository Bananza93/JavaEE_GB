package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
    List<Product> findAllProductsWithinPriceRange(Float minPrice, Float maxPrice);
}
