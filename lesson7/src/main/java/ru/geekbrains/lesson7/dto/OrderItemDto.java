package ru.geekbrains.lesson7.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {

    private ProductDto product;
    private Integer quantity;
    private BigDecimal price;
}
