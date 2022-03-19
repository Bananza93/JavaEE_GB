package ru.geekbrains.lesson7.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.geekbrains.lesson7.dto.CategoryDto;
import ru.geekbrains.lesson7.model.Category;
import ru.geekbrains.lesson7.model.Product;

@Component
public class CategoryMapper {

    private final ProductMapper productMapper;

    public CategoryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CategoryDto categoryToCategoryDto(Category category, Pageable pageable) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImageURL(category.getImageURL());

        int start = Math.min((int) pageable.getOffset(), category.getProduct().size());
        int end = Math.min((start + pageable.getPageSize()), category.getProduct().size());
        Page<Product> page = new PageImpl<>(category.getProduct().subList(start, end), pageable, category.getProduct().size());

        dto.setProducts(page.map(productMapper::productToProductDto));
        return dto;
    }

}
