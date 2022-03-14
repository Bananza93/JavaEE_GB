package ru.geekbrains.lesson7.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetailsDto {

    private Long id;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private String orderStatus;
    private String deliveryAddress;
    private String createdAt;
}
