package ru.geekbrains.lesson7.dto;

import lombok.Data;
import ru.geekbrains.lesson7.model.Product;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    @NotNull(message = "{validation.Product.TitleEmptyOrNull.message}")
    @NotEmpty(message = "{validation.Product.TitleEmptyOrNull.message}")
    private String name;

    private String description;

    private String imageURL;

    @NotNull
    @NotEmpty
    private String category;

    private Integer quantity;

    @DecimalMin(value = Product.STR_MIN_PRICE, message = "{validation.Product.PriceMin.message}")
    @DecimalMax(value = Product.STR_MAX_PRICE, message = "{validation.Product.PriceMax.message}")
    @Digits(integer = 7, fraction = 2, message = "{validation.Product.PriceDigits.message}")
    private BigDecimal price;

}
