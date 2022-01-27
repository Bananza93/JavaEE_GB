package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="products")
@SecondaryTable(name = "product_price_histories", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id"))
@DynamicInsert
@DynamicUpdate
public class Product {

    public static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    public static final BigDecimal MAX_PRICE = BigDecimal.valueOf(9_999_999.99);
    public static final String STR_MIN_PRICE = "0.0";
    public static final String STR_MAX_PRICE = "9999999.99";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "image")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "price", table = "product_price_histories", nullable = false)
    private BigDecimal price;
}
