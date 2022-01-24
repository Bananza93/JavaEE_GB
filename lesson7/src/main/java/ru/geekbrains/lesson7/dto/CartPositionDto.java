package ru.geekbrains.lesson7.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartPositionDto {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer qnt;
}
