package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.mapper.CartMapper;
import ru.geekbrains.lesson7.model.Cart;

import javax.annotation.PostConstruct;

@Service
@SessionScope
public class CartService {

    private final CartMapper cartMapper;
    private final ProductService productService;
    private Cart cart;

    public CartService(CartMapper cartMapper, ProductService productService) {
        this.cartMapper = cartMapper;
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    @TrackExecutionTime
    public CartDto getCartDto() {
        return cartMapper.cartToCartDto(cart);
    }

    @TrackExecutionTime
    public Cart getCart() {
        return cart;
    }

    @TrackExecutionTime
    public CartDto addToCart(Long id, Integer qnt) {
        productService.getProductById(id).ifPresent(product -> cart.addProduct(product, qnt));
        return getCartDto();
    }

    @TrackExecutionTime
    public CartDto removeFromCart(Long id, Integer qnt) {
        cart.removeProduct(id, qnt);
        return getCartDto();
    }

}
