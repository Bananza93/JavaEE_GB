package ru.geekbrains.lesson7.controller.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.filter.CookieFilter;
import ru.geekbrains.lesson7.service.CartService;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<CartDto> getCart(@CookieValue(name = CookieFilter.ANON_CART_COOKIE_NAME, required = false) String anonCartCookie) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        var responseBuilder = ResponseEntity.ok();
        if (!(principal instanceof AnonymousAuthenticationToken)) {
            var cookie = ResponseCookie.from(CookieFilter.ANON_CART_COOKIE_NAME, anonCartCookie)
                    .httpOnly(true)
                    .path("/lesson7")
                    .maxAge(0)
                    .build();
            responseBuilder.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return responseBuilder.body(cartService.getCartDto(principal, anonCartCookie));
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
