package ru.geekbrains.lesson9.mapper;

import ru.geekbrains.lesson9.bean.Cart;
import ru.geekbrains.lesson9.dto.CartDto;

public class CartMapper {
    public static CartDto cartToCartDto(Cart cart) {
        if (cart == null) return null;
        CartDto cartDto = new CartDto();
        cartDto.setCurrentCart(cart.getCurrentCart().values().stream().toList());
        cartDto.setSumPrice(cart.getSumPrice());
        return cartDto;
    }
}
