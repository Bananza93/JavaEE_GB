package ru.geekbrains.lesson7.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Integer productCount;
    private List<CartPositionDto> currentCart;
    private BigDecimal sumPrice;
}
