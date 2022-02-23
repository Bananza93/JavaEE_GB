package ru.geekbrains.lesson7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPositionDto {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer qnt;

}
