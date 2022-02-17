package ru.geekbrains.BackService.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.geekbrains.BackService.mapper.ProductMapper;
import ru.geekbrains.BackService.service.ProductService;
import ru.geekbrains.ProductDto;
import ru.geekbrains.ResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseDto addProduct(@RequestBody ProductDto productDto) {
        ResponseDto responseDto = new ResponseDto();
        productService.addProduct(productMapper.productDtoToProduct(productDto));
        responseDto.setMessage("Product added");
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteProduct(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto();
        productService.deleteProduct(id);
        responseDto.setMessage("Product deleted");
        return responseDto;
    }
}
