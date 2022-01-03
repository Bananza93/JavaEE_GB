package ru.geekbrains.lesson9.dto;

import lombok.Data;
import ru.geekbrains.lesson9.bean.common.CartPosition;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private List<CartPosition> currentCart;
    private BigDecimal sumPrice;
}
