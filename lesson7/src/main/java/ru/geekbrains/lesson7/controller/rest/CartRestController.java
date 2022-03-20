package ru.geekbrains.lesson7.controller.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.service.CartService;
import ru.geekbrains.lesson7.service.OrderService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public CartDto getCart() {
        return cartService.getCartDto();
    }

    @PostMapping("/product")
    public CartDto addToCart(@RequestParam Long id, @RequestParam Integer qnt) {
        return cartService.addToCart(id, qnt);
    }

    @DeleteMapping("/product")
    public CartDto removeFromCart(@RequestParam Long id, @RequestParam Integer qnt) {
        return cartService.removeFromCart(id, qnt);
    }
}
