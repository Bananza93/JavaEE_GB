package ru.geekbrains.lesson7.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.service.CartService;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartRestControllerTest {

    private CartRestController controller;

    private CartDto cartDto1;
    private CartDto cartDto2;

    @BeforeEach
    void setUp() {
        cartDto1 = CartDto.builder().build();
        cartDto2 = CartDto.builder().build();

        CartService cartService = mock(CartService.class);

        when(cartService.getCartDto(null, null)).thenReturn(cartDto1);
        when(cartService.addToCart(1L, 2)).thenReturn(cartDto2);
        when(cartService.removeFromCart(1L, 2)).thenReturn(cartDto1);

        controller = new CartRestController(cartService);
    }

    @Test
    void getCart() {
        CartDto cart = controller.getCart(null).getBody();
        assertSame(cartDto1, cart);
    }

    @Test
    void addToCart() {
        CartDto cart = controller.addToCart(1L, 2);
        assertSame(cartDto2, cart);
    }

    @Test
    void removeFromCart() {
        CartDto cart = controller.removeFromCart(1L, 2);
        assertSame(cartDto1, cart);
    }
}