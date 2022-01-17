package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.service.ProductService;
import ru.geekbrains.lesson7.service.UserService;

import javax.validation.Valid;

import static ru.geekbrains.lesson7.mapper.ProductMapper.productDtoToProduct;
import static ru.geekbrains.lesson7.mapper.ProductMapper.productToProductDto;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService userService;
    private final ProductService productService;

    public AdminPanelController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/users/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersDto());
        return "user_list";
    }

    @GetMapping("/products/add")
    public String getAddNewProductForm() {
        return "new_product_form";
    }

    @PostMapping("/products/add")
    public String addNewProduct(@Valid ProductDto productDto) {
        productService.addProduct(productDtoToProduct(productDto));
        return "new_product_form";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String getEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productToProductDto(productService.getProductById(id)));
        return "edit_product_form";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid ProductDto productDto) {
        productService.editProduct(id, productDtoToProduct(productDto));
        return "redirect:/products/info/{id}";
    }
}
