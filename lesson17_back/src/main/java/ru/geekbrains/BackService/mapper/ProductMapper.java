package ru.geekbrains.BackService.mapper;

import org.springframework.stereotype.Component;
import ru.geekbrains.BackService.model.Category;
import ru.geekbrains.BackService.model.Product;
import ru.geekbrains.BackService.service.CategoryService;
import ru.geekbrains.ProductDto;

@Component
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Product productDtoToProduct(ProductDto productDto) {
        if (productDto == null) return null;
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(getCategoryFromCategoryTitle(productDto.getCategory()));
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        return product;
    }

    public ProductDto productToProductDto(Product product) {
        if (product == null) return null;
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory().getName());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        return productDto;
    }

    private Category getCategoryFromCategoryTitle(String name) {
        return categoryService.getCategoryByName(name);
    }
}
