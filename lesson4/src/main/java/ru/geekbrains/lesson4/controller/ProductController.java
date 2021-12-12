package ru.geekbrains.lesson4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.lesson4.dto.Product;
import ru.geekbrains.lesson4.service.ProductService;


@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product_list";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product_info";
    }

    @GetMapping("/add")
    public String getAddNewProductForm() {
        return "new_product_form";
    }

    @PostMapping("/add")
    public String addNewProduct(Product product) {
        productService.addNewProduct(product);
        return "redirect:/add";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
