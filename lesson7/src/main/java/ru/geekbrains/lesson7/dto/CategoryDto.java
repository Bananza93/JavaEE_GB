package ru.geekbrains.lesson7.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private String imageURL;
    private Page<ProductDto> products;
}
