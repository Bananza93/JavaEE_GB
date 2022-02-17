package ru.geekbrains.FrontService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ru.geekbrains.FrontService.feignClient.BackService;
import ru.geekbrains.FrontService.feignClient.exception.FeignClientException;
import ru.geekbrains.ProductDto;
import ru.geekbrains.ResponseDto;

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
        try {
            ResponseDto responseDto = backService.addProduct(productDto);
            model.addAttribute("response", responseDto);
        } catch (FeignClientException e) {
            model.addAttribute("errorResponse", e);
        }
        return "new_product_form";
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteProduct(@PathVariable Long id, final RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", backService.deleteProduct(id));
        return new RedirectView("/products", true);
    }
}
