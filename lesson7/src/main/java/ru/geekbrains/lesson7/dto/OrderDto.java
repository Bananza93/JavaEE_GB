package ru.geekbrains.lesson7.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDto {

    private Long id;
    private BigDecimal totalPrice;
    private OrderStatusDto status;
    private String managerEmail;
    private String createdAt;
    private String lastChangeStatusDate;
}
