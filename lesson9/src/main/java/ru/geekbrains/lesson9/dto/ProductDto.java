package ru.geekbrains.lesson9.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.lesson9.model.Product;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {

    @NotNull(message = "{validation.Product.TitleEmptyOrNull.message}")
    @NotEmpty(message = "{validation.Product.TitleEmptyOrNull.message}")
    private String title;

    @DecimalMin(value = Product.STR_MIN_PRICE, message = "{validation.Product.PriceMin.message}")
    @DecimalMax(value = Product.STR_MAX_PRICE, message = "{validation.Product.PriceMax.message}")
    @Digits(integer = 7, fraction = 2, message = "{validation.Product.PriceDigits.message}")
    private BigDecimal price;
}
