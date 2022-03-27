package ru.geekbrains.lesson7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDto {

    private Long id;
    private String name;
    private String description;
}
