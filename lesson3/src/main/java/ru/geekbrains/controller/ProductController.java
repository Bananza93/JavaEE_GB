package ru.geekbrains.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.dto.Product;
import ru.geekbrains.service.ProductService;

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
    public String addNewProduct(@RequestParam Integer id,
                                @RequestParam String title,
                                @RequestParam Double cost) {
        productService.addNewProduct(new Product(id, title, cost));
        return "redirect:/add";
    }
}
