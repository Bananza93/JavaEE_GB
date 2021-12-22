package ru.geekbrains.lesson7.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")
@Data
public class Product {

    public static final Float MIN_PRICE = 0.0f;
    public static final Float MAX_PRICE = 9_999_999.0f;
    public static final String STR_MIN_PRICE = "0.0";
    public static final String STR_MAX_PRICE = "9999999.0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Float price;
}
