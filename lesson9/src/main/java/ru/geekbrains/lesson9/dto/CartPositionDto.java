package ru.geekbrains.lesson9.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CartPositionDto {
    @NotNull(message = "{validation.Cart.ProductIdNotNull.message}")
    @Min(value = 1, message = "{validation.Cart.ProductIdMin.message}")
    private Long productId;

    @NotNull(message = "{validation.Cart.QntNotNull.message}")
    @Min(value = 1, message = "{validation.Cart.QntMin.message}")
    @Max(value = 99, message = "{validation.Cart.QntMax.message}")
    private Integer qnt;
}
