package ru.geekbrains.lesson7.dto;

import lombok.Data;
import ru.geekbrains.lesson7.model.CartPosition;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private List<CartPosition> currentCart;
    private BigDecimal sumPrice;
}
