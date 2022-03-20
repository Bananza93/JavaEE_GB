package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class CartPositionProduct {

    private Long id;
    private String name;
    private BigDecimal price;
}
