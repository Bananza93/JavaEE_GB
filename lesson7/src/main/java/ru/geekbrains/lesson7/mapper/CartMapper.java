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
        return CartDto.builder()
                .productCount(cart.getProductCount())
                .currentCart(cart.getCurrentCart().stream().map(this::cartPositionToCartPositionDto).toList())
                .sumPrice(cart.getSumPrice())
                .build();
    }

    public CartPositionDto cartPositionToCartPositionDto(CartPosition cartPosition) {
        if (cartPosition == null) return null;
        return CartPositionDto.builder()
                .productId(cartPosition.getProduct().getId())
                .title(cartPosition.getProduct().getName())
                .price(cartPosition.getPositionPrice())
                .qnt(cartPosition.getQnt())
                .build();
    }

    public CartPositionProduct productToCartPositionProduct(Product product) {
        if (product == null) return null;
        return new CartPositionProduct(product.getId(), product.getName(), product.getPrice());
    }
}
