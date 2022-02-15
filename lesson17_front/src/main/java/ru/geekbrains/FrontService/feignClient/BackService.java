package ru.geekbrains.FrontService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.FrontService.dto.ProductDto;
import ru.geekbrains.FrontService.dto.ResponseDto;

import java.util.List;

@FeignClient("BackService")
public interface BackService {

    @GetMapping("/rest/products")
    List<ProductDto> getAllProducts();

    @PostMapping("/rest/products/add")
    ResponseDto addProduct(@RequestBody ProductDto productDto);

    @DeleteMapping("/rest/products/delete/{id}")
    ResponseDto deleteProduct(@PathVariable Long id);
}
