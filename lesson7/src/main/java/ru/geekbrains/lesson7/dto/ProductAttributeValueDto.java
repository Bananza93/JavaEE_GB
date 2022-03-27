package ru.geekbrains.lesson7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeValueDto {

    private Long id;
    private ProductAttributeDto attribute;
    private String value;
}
