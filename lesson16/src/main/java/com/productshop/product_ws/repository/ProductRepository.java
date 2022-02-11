package com.productshop.product_ws.repository;

import com.productshop.product_ws.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("from Product p left join fetch p.category where p.price between :minPrice and :maxPrice")
    List<Product> getAllProductsByPriceRange(double minPrice, double maxPrice);
}
