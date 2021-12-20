package ru.geekbrains.lesson7.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private String title;
    private Float price;
}
