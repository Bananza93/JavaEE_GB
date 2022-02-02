package ru.geekbrains.lesson7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = "from Product p left join fetch p.category where p.price between :minPrice and :maxPrice",
            countQuery = "select count(p) from Product p left join p.category where p.price between :minPrice and :maxPrice"
    )
    Page<Product> findAllProductsWithinPriceRangeByPage(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
