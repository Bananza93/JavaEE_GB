package ru.geekbrains.lesson7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.model.ProductAttribute;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = "from Product p left join fetch p.category where p.price between :minPrice and :maxPrice",
            countQuery = "select count(p) from Product p left join p.category where p.price between :minPrice and :maxPrice"
    )
    Page<Product> findAllProductsWithinPriceRangeByPage(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    @Query("FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.productCharacteristics pc left join fetch pc.attribute WHERE p.id = :id")
    Optional<Product> getProductById(Long id);

    @Query("FROM ProductAttribute pa")
    List<ProductAttribute> getAllProductAttributes();

    @Query("FROM Product p LEFT JOIN FETCH p.category WHERE p.id IN :productsId")
    List<Product> getProductsById(@Param("productsId") List<Long> productsId);

    @Query("FROM Product p LEFT JOIN FETCH p.category")
    List<Product> getAllProducts();
}
