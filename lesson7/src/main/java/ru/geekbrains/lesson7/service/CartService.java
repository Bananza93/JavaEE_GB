package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.mapper.CartMapper;
import ru.geekbrains.lesson7.model.Cart;

@Service
@SessionScope
public class CartService {

    private final CartMapper cartMapper;
    private final ProductService productService;
    private final Cart cart;

    public CartService(CartMapper cartMapper, ProductService productService, Cart cart) {
        this.cartMapper = cartMapper;
        this.productService = productService;
        this.cart = cart;
    }

    public CartDto getCart() {
        return cartMapper.cartToCartDto(cart);
    }

    public CartDto addToCart(Long id, Integer qnt) {
        productService.getProductById(id).ifPresent(product -> cart.addProduct(product, qnt));
        return cartMapper.cartToCartDto(cart);
    }

    public CartDto removeFromCart(Long id, Integer qnt) {
        cart.removeProduct(id, qnt);
        return cartMapper.cartToCartDto(cart);
    }
}
