package ru.geekbrains.lesson7.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.mapper.ProductMapper;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.ProductService;
import ru.geekbrains.lesson7.service.UserService;

import javax.validation.Valid;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService userService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public AdminPanelController(UserService userService, ProductService productService, ProductMapper productMapper) {
        this.userService = userService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersDto());
        return "admin/user_list";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = Product.STR_MIN_PRICE) BigDecimal minPrice,
                                 @RequestParam(defaultValue = Product.STR_MAX_PRICE) BigDecimal maxPrice,
                                 @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("products", productService.getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable));
        return "admin/product_list";
    }

    @GetMapping("/products/add")
    public String getAddNewProductForm() {
        return "admin/new_product_form";
    }

    @PostMapping("/products/add")
    public String addNewProduct(@Valid ProductDto productDto) {
        productService.addProduct(productMapper.productDtoToProduct(productDto));
        return "admin/new_product_form";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String getEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productMapper.productToProductDto(productService.getProductById(id).orElse(null)));
        return "admin/edit_product_form";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid ProductDto productDto) {
        productService.editProduct(id, productMapper.productDtoToProduct(productDto));
        return "redirect:/admin/products/info/{id}";
    }

    @GetMapping("products/info/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/product_info";
    }
}
