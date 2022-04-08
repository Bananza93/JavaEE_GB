package ru.geekbrains.lesson7.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartDto {
    private Integer productCount;
    private List<CartPositionDto> currentCart;
    private BigDecimal sumPrice;
}
