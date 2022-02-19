package ru.geekbrains.FrontService.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.FrontService.config.FeignClientConfig;
import ru.geekbrains.ProductDto;
import ru.geekbrains.ResponseDto;

import java.util.List;

@FeignClient(value = "gateway", configuration = FeignClientConfig.class)
public interface BackService {

    @GetMapping("/shop/rest/products")
    List<ProductDto> getAllProducts();

    @PostMapping("/shop/rest/products/add")
    ResponseDto addProduct(@RequestBody ProductDto productDto);

    @DeleteMapping("/shop/rest/products/delete/{id}")
    ResponseDto deleteProduct(@PathVariable Long id);
}
