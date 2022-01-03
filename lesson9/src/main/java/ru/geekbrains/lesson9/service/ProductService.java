package ru.geekbrains.lesson9.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.lesson9.dto.ProductDto;
import ru.geekbrains.lesson9.mapper.ProductMapper;
import ru.geekbrains.lesson9.model.Product;
import ru.geekbrains.lesson9.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProductDtoById(Long id) {
        return ProductMapper.productToProductDto(getProductById(id));
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductDto> getAllProductsDtoWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return getAllProductsWithPriceRange(minPrice, maxPrice).stream()
                .map(ProductMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    public List<Product> getAllProductsWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(Product.MIN_PRICE) < 0 || minPrice.compareTo(Product.MAX_PRICE) > 0)
            minPrice = Product.MIN_PRICE;
        if (maxPrice.compareTo(Product.MIN_PRICE) < 0 || maxPrice.compareTo(Product.MAX_PRICE) > 0)
            maxPrice = Product.MAX_PRICE;
        if (maxPrice.compareTo(minPrice) < 0) {
            BigDecimal tmpMinPrice = minPrice;
            minPrice = maxPrice;
            maxPrice = tmpMinPrice;
        }
        return productRepository.findAllProductsWithinPriceRange(minPrice, maxPrice);
    }

    public void addProduct(ProductDto productDto) {
        productRepository.save(ProductMapper.productDtoToProduct(productDto));
    }

    public void updateProduct(Long id, ProductDto productDto) {
        if (productRepository.existsById(id)) {
            Product product = ProductMapper.productDtoToProduct(productDto);
            product.setId(id);
            productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id = " + id + " not found");
        }
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id = " + id + " not found");
        }
    }

}
