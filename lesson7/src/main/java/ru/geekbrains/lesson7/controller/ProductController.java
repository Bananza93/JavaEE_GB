package ru.geekbrains.lesson7.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.ProductService;

import static ru.geekbrains.lesson7.mapper.ProductMapper.productDtoToProduct;


@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = Product.STR_MIN_PRICE) Float minPrice,
                                 @RequestParam(defaultValue = Product.STR_MAX_PRICE) Float maxPrice,
                                 @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("products", productService.getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable));
        return "product_list";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product_info";
    }

    @GetMapping("/add")
    public String getAddNewProductForm() {
        return "new_product_form";
    }

    @PostMapping("/add")
    public String addNewProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDtoToProduct(productDto));
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
