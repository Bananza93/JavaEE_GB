package ru.geekbrains.BackService.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.BackService.dto.ProductDto;
import ru.geekbrains.BackService.dto.ResponseDto;
import ru.geekbrains.BackService.mapper.ProductMapper;
import ru.geekbrains.BackService.service.ProductService;

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
        try {
            productService.addProduct(productMapper.productDtoToProduct(productDto));
            responseDto.setStatusCode("200");
            responseDto.setMessage("Product added");
        } catch (Exception e) {
            responseDto.setStatusCode("400");
            responseDto.setMessage("Caught error! " + e.getMessage());
        }
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteProduct(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            productService.deleteProduct(id);
            responseDto.setStatusCode("200");
            responseDto.setMessage("Product deleted");
        } catch (Exception e) {
            responseDto.setStatusCode("400");
            responseDto.setMessage("Caught error! Message: " + e.getMessage());
        }
        return responseDto;
    }
}
