package ru.geekbrains.lesson7.mapper;


import org.springframework.stereotype.Component;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.dto.CartPositionDto;
import ru.geekbrains.lesson7.model.Cart;
import ru.geekbrains.lesson7.model.CartPosition;
import ru.geekbrains.lesson7.model.CartPositionProduct;
import ru.geekbrains.lesson7.model.Product;

@Component
public class CartMapper {
    public CartDto cartToCartDto(Cart cart) {
        if (cart == null) return null;
        CartDto cartDto = new CartDto();
        cartDto.setProductCount(cart.getProductCount());
        cartDto.setCurrentCart(cart.getCurrentCart().values().stream().map(this::cartPositionToCartPositionDto).toList());
        cartDto.setSumPrice(cart.getSumPrice());
        return cartDto;
    }

    public CartPositionDto cartPositionToCartPositionDto(CartPosition cartPosition) {
        if (cartPosition == null) return null;
        CartPositionDto cartPositionDto = new CartPositionDto();
        cartPositionDto.setProductId(cartPosition.getProduct().getId());
        cartPositionDto.setTitle(cartPosition.getProduct().getName());
        cartPositionDto.setPrice(cartPosition.getPositionPrice());
        cartPositionDto.setQnt(cartPosition.getQnt());
        return cartPositionDto;
    }

    public CartPositionProduct productToCartPositionProduct(Product product) {
        if (product == null) return null;
        return new CartPositionProduct(product.getId(), product.getName(), product.getPrice());
    }
}
