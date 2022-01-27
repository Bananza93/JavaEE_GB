package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.mapper.CartMapper;
import ru.geekbrains.lesson7.model.Cart;

import javax.annotation.PostConstruct;
import java.security.Principal;

@Service
@SessionScope
public class CartService {

    private final CartMapper cartMapper;
    private final ProductService productService;
    private final OrderService orderService;
    private Cart cart;

    public CartService(CartMapper cartMapper, ProductService productService, OrderService orderService) {
        this.cartMapper = cartMapper;
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    private void clearCart() {
        cart.clear();
    }

    public CartDto getCartDto() {
        return cartMapper.cartToCartDto(cart);
    }

    public CartDto addToCart(Long id, Integer qnt) {
        productService.getProductById(id).ifPresent(product -> cart.addProduct(product, qnt));
        return getCartDto();
    }

    public CartDto removeFromCart(Long id, Integer qnt) {
        cart.removeProduct(id, qnt);
        return getCartDto();
    }

    @Transactional
    public CartDto proceedOrder(Principal principal) {
        orderService.makeOrder(cart, principal);
        clearCart();
        return getCartDto();
    }
}
