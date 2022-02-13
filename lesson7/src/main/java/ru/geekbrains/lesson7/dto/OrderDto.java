package ru.geekbrains.lesson7.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {

    private Long id;
    private BigDecimal totalPrice;
}