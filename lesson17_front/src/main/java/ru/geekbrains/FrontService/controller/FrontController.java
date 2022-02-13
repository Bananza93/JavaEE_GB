package ru.geekbrains.FrontService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.FrontService.dto.ProductDto;
import ru.geekbrains.FrontService.feignClient.BackService;

@Controller
@RequestMapping("/products")
public class FrontController {

    private final BackService backService;

    public FrontController(BackService backService) {
        this.backService = backService;
    }

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", backService.getAllProducts());
        return "product_list";
    }

    @GetMapping("/add")
    public String getAddForm() {
        return "new_product_form";
    }

    @PostMapping("/add")
    public String addNewProduct(Model model, ProductDto productDto) {
        model.addAttribute("response", backService.addProduct(productDto));
        return "new_product_form";
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteProduct(@PathVariable Long id, final RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", backService.deleteProduct(id));
        return new RedirectView("/products", true);
    }
}
