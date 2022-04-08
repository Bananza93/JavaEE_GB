package ru.geekbrains.lesson7.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartPositionDto {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer qnt;

}
