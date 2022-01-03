package ru.geekbrains.lesson9.dto;

import lombok.Data;
import ru.geekbrains.lesson9.model.Product;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    @NotNull(message = "{validation.Product.TitleEmptyOrNull.message}")
    @NotEmpty(message = "{validation.Product.TitleEmptyOrNull.message}")
    private String title;

    @DecimalMin(value = Product.STR_MIN_PRICE, message = "{validation.Product.PriceMin.message}")
    @DecimalMax(value = Product.STR_MAX_PRICE, message = "{validation.Product.PriceMax.message}")
    @Digits(integer = 7, fraction = 2, message = "{validation.Product.PriceDigits.message}")
    private BigDecimal price;
}
