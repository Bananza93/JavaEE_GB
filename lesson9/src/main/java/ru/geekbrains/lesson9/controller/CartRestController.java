package ru.geekbrains.lesson9.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.lesson9.dto.CartDto;
import ru.geekbrains.lesson9.dto.CartPositionDto;
import ru.geekbrains.lesson9.service.CartService;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public CartDto getCart() {
        return cartService.getCart();
    }

    @PostMapping("/add")
    public void addToCart(@Valid @RequestBody CartPositionDto cartPositionDto) {
        cartService.addToCart(cartPositionDto);
    }

    @PostMapping("/remove")
    public void removeFromCart(@Valid @RequestBody CartPositionDto cartPositionDto) {
        cartService.removeFromCart(cartPositionDto);
    }
}
