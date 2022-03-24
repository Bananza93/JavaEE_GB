package ru.geekbrains.lesson7.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.dto.CartDto;
import ru.geekbrains.lesson7.filter.CookieFilter;
import ru.geekbrains.lesson7.mapper.CartMapper;
import ru.geekbrains.lesson7.model.Cart;
import ru.geekbrains.lesson7.repository.CartRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;

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
    private CartDto getCartDto(Cart cart) {
        return cartMapper.cartToCartDto(cart);
    }

    @TrackExecutionTime
    public CartDto getCartDto(Principal principal, String anonCartUUID) {
        return getCartDto(getCartForCurrentUser(principal, anonCartUUID));
    }

    @TrackExecutionTime
    public Cart getCartForCurrentUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String anonCartUUID = getAnonCartCookieValue();
        return getCartForCurrentUser(principal, anonCartUUID);
    }

    @TrackExecutionTime
    @Transactional
    public Cart getCartForCurrentUser(Principal principal, String anonCartUUID) {
        if (principal instanceof AnonymousAuthenticationToken) {
            return cartRepository.findById(anonCartUUID).orElse(new Cart(anonCartUUID));
        } else {
            Cart userCart = cartRepository.findById(principal.getName()).orElse(new Cart(principal.getName()));
            if (anonCartUUID != null) {
                Cart anonCart = cartRepository.findById(anonCartUUID).orElse(null);
                if (anonCart != null && !anonCart.getCurrentCart().isEmpty()) {
                    mergeAnonCartToUserCart(userCart, anonCart);
                }
            }
            return userCart;
        }
    }

    private void mergeAnonCartToUserCart(Cart userCart, Cart anonCart) {
        anonCart.getCurrentCart().forEach(e -> userCart.addProduct(e.getProduct(), e.getQnt()));
        cartRepository.save(userCart);
        cartRepository.delete(anonCart);
    }

    private String getAnonCartCookieValue() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
            Cookie cookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equalsIgnoreCase(CookieFilter.ANON_CART_COOKIE_NAME))
                    .findFirst()
                    .orElse(null);
            return cookie == null ? null : cookie.getValue();
        }
        return null;
    }

    @TrackExecutionTime
    @Transactional
    public CartDto addToCart(Long id, Integer qnt) {
        Cart cart = getCartForCurrentUser();
        productService.getProductById(id).ifPresent(product -> cart.addProduct(cartMapper.productToCartPositionProduct(product), qnt));
        cartRepository.save(cart);
        return getCartDto(cart);
    }

    @TrackExecutionTime
    @Transactional
    public CartDto removeFromCart(Long id, Integer qnt) {
        Cart cart = getCartForCurrentUser();
        cart.removeProduct(id, qnt);
        cartRepository.save(cart);
        return getCartDto(cart);
    }

    @TrackExecutionTime
    @Transactional
    public void removeCartForCurrentUser() {
        cartRepository.delete(getCartForCurrentUser());
    }

}
