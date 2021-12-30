package ru.geekbrains.lesson9.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.lesson9.dto.ProductDto;
import ru.geekbrains.lesson9.model.Product;
import ru.geekbrains.lesson9.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rest/products")
public class ProductsRestController {

    ProductService productService;

    public ProductsRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProductDtoById(id);
    }

    @GetMapping()
    public List<ProductDto> getAllProducts(@RequestParam(defaultValue = Product.STR_MIN_PRICE) BigDecimal minPrice,
                                           @RequestParam(defaultValue = Product.STR_MAX_PRICE) BigDecimal maxPrice) {
        return productService.getAllProductsDtoWithPriceRange(minPrice, maxPrice);
    }

    @PostMapping("/add")
    public void addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PutMapping("/update/{id}")
    public void updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
