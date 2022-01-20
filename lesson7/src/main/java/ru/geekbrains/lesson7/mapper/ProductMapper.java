package ru.geekbrains.lesson7.mapper;

import org.springframework.stereotype.Component;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.model.Category;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.CategoryService;

@Component
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Product productDtoToProduct(ProductDto productDto) {
        if (productDto == null) return null;
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(getCategoryFromCategoryTitle(productDto.getCategory()));
        return product;
    }

    public ProductDto productToProductDto(Product product) {
        if (product == null) return null;
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getTitle());
        return productDto;
    }

    private Category getCategoryFromCategoryTitle(String title) {
        return categoryService.getCategoryByTitle(title);
    }
}
