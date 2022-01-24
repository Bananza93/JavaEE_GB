package ru.geekbrains.lesson7.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartRestController {

  private final CartService cartService;

  public CartRestController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping()
  public CartDto getCart() {
    return cartService.getCart();
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
