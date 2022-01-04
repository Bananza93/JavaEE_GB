package ru.geekbrains.lesson7.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="products")
@Data
public class Product {

    public static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    public static final BigDecimal MAX_PRICE = BigDecimal.valueOf(9_999_999.99);
    public static final String STR_MIN_PRICE = "0.0";
    public static final String STR_MAX_PRICE = "9999999.99";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;
}
