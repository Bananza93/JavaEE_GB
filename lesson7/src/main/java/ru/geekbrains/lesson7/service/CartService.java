package ru.geekbrains.lesson7.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.mapper.CartMapper;
import ru.geekbrains.lesson7.model.Cart;
import ru.geekbrains.lesson7.repository.CartRepository;

import java.security.Principal;
import java.util.Objects;

@Service
public class CartService {

    private final CartMapper cartMapper;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartService(CartMapper cartMapper, ProductService productService, CartRepository cartRepository) {
        this.cartMapper = cartMapper;
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    @TrackExecutionTime
    public CartDto getCartDto() {
        return getCartDto(null);
    }

    @TrackExecutionTime
    public CartDto getCartDto(Cart cart) {
        return cartMapper.cartToCartDto(cart == null ? getCartForCurrentUser() : cart);
    }

    @TrackExecutionTime
    public Cart getCartForCurrentUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId();
        return getCart(principal, sessionId);
    }

    private Cart getCart(Principal principal, String sessionId) {
        if (principal instanceof AnonymousAuthenticationToken) {
            return cartRepository.findById(sessionId).orElse(new Cart(sessionId));
        }
        return cartRepository.findById(principal.getName()).orElse(new Cart(principal.getName()));
    }

    @TrackExecutionTime
    public CartDto addToCart(Long id, Integer qnt) {
        Cart cart = getCartForCurrentUser();
        productService.getProductById(id).ifPresent(product -> cart.addProduct(product, qnt));
        cartRepository.save(cart);
        return getCartDto(cart);
    }

    @TrackExecutionTime
    public CartDto removeFromCart(Long id, Integer qnt) {
        Cart cart = getCartForCurrentUser();
        cart.removeProduct(id, qnt);
        cartRepository.save(cart);
        return getCartDto(cart);
    }

    @TrackExecutionTime
    public void removeCartForCurrentUser() {
        cartRepository.delete(getCartForCurrentUser());
    }

}
