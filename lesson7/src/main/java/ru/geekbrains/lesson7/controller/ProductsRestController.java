package ru.geekbrains.lesson7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static ru.geekbrains.lesson7.mapper.ProductMapper.productDtoToProduct;

@RestController
@RequestMapping("/rest")
public class ProductsRestController {

    ProductService productService;

    public ProductsRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestParam(defaultValue = Product.STR_MIN_PRICE) BigDecimal minPrice,
                                        @RequestParam(defaultValue = Product.STR_MAX_PRICE) BigDecimal maxPrice) {
        return productService.getAllProductsWithPriceRange(minPrice, maxPrice);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDtoToProduct(productDto));
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
