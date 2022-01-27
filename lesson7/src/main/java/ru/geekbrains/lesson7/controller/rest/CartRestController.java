package ru.geekbrains.lesson7.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.service.CartService;
import ru.geekbrains.lesson7.service.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService cartService;
    private final OrderService orderService;

    public CartRestController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
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

    @PostMapping("/makeOrder")
    public CartDto makeOrder(Principal principal) {
        return cartService.proceedOrder(principal);
    }
}
