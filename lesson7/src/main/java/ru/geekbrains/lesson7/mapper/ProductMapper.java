package ru.geekbrains.lesson7.mapper;

import org.springframework.stereotype.Component;
import ru.geekbrains.lesson7.dto.ProductAttributeDto;
import ru.geekbrains.lesson7.dto.ProductAttributeValueDto;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.model.Category;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.model.ProductAttribute;
import ru.geekbrains.lesson7.model.ProductAttributeValue;
import ru.geekbrains.lesson7.service.CategoryService;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Product productDtoToProduct(ProductDto productDto) {
        if (productDto == null) return null;
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setCategory(getCategoryFromCategoryTitle(productDto.getCategory()));
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setIsAvailable(productDto.getIsAvailable());
        product.setProductCharacteristics(productDto.getProductCharacteristics().stream()
                .map(e -> new ProductAttributeValue(
                        e.getId(),
                        new ProductAttribute(
                                e.getAttribute().getId(),
                                e.getAttribute().getName(),
                                e.getAttribute().getDescription()
                        ),
                        e.getValue()
                ))
                .collect(Collectors.toList())
        );
        return product;
    }

    public ProductDto productToProductDto(Product product) {
        if (product == null) return null;
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageURL(product.getImageURL())
                .category(product.getCategory().getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .isAvailable(product.getIsAvailable())
                .productCharacteristics(product.getProductCharacteristics().stream()
                        .map(e -> new ProductAttributeValueDto(
                                e.getId(),
                                new ProductAttributeDto(
                                        e.getAttribute().getId(),
                                        e.getAttribute().getName(),
                                        e.getAttribute().getDescription()
                                ),
                                e.getValue()
                        ))
                        .collect(Collectors.toList()))
                .build();
    }

    private Category getCategoryFromCategoryTitle(String name) {
        return categoryService.getCategoryByName(name);
    }
}
