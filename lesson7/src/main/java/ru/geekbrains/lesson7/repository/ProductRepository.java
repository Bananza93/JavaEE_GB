package ru.geekbrains.lesson7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
    Page<Product> findAllProductsWithinPriceRangeByPage(Float minPrice, Float maxPrice, Pageable pageable);
}
