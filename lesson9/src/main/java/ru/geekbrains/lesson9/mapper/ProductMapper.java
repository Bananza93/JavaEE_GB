package ru.geekbrains.lesson9.mapper;

import ru.geekbrains.lesson9.dto.ProductDto;
import ru.geekbrains.lesson9.model.Product;

public class ProductMapper {

    public static Product productDtoToProduct(ProductDto productDto) {
        if (productDto == null) return null;
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public static ProductDto productToProductDto(Product product) {
        if (product == null) return null;
        ProductDto productDto = new ProductDto();
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
