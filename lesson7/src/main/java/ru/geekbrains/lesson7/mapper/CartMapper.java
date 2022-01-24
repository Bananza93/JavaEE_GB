package ru.geekbrains.lesson7.mapper;


import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.model.Cart;

public class CartMapper {
    public CartDto cartToCartDto(Cart cart) {
        if (cart == null) return null;
        CartDto cartDto = new CartDto();
        cartDto.setCurrentCart(cart.getCurrentCart().values().stream().toList());
        cartDto.setSumPrice(cart.getSumPrice());
        return cartDto;
    }
}
