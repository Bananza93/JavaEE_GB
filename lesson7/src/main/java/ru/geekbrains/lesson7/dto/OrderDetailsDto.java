package ru.geekbrains.lesson7.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDetailsDto {

    private Long id;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private OrderStatusDto status;
    private String managerEmail;
    private String deliveryAddress;
    private String createdAt;
}
