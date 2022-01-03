package ru.geekbrains.lesson7.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.ProductService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.net.http.HttpResponse;

import static ru.geekbrains.lesson7.mapper.ProductMapper.productDtoToProduct;
import static ru.geekbrains.lesson7.mapper.ProductMapper.productToProductDto;


@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = Product.STR_MIN_PRICE) BigDecimal minPrice,
                                 @RequestParam(defaultValue = Product.STR_MAX_PRICE) BigDecimal maxPrice,
                                 @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("products", productService.getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable));
        return "product_list";
    }

    @GetMapping("/info/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product_info";
    }

    @GetMapping("/add")
    public String getAddNewProductForm() {
        return "new_product_form";
    }

    @PostMapping("/add")
    public String addNewProduct(@Valid ProductDto productDto) {
        productService.addProduct(productDtoToProduct(productDto));
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String getEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productToProductDto(productService.getProductById(id)));
        return "edit_product_form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid ProductDto productDto) {
        productService.editProduct(id, productDtoToProduct(productDto));
        return "redirect:/products/info/{id}";
    }
}
