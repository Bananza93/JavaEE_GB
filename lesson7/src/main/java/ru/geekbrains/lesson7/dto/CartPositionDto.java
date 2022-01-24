package ru.geekbrains.lesson7.dto;

import lombok.Data;

@Data
public class CartPositionDto {
    private Long productId;
    private Integer qnt;
}
