package ru.geekbrains.lesson7.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
import java.util.UUID;

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
        return getCart(principal);
    }

    private Cart getCart(Principal principal) {
        String anonCartUUID = UUID.randomUUID().toString();//getAnonCartCookieValue();
        System.out.println("Anon cart cookie = " + anonCartUUID);
        if (principal instanceof AnonymousAuthenticationToken) {
            Cart cart = cartRepository.getCartById(anonCartUUID).orElse(new Cart(anonCartUUID));
            System.out.println("Find cart with size = " + cart.getCurrentCart().size());
            return cart;
        } else {
            Cart userCart = cartRepository.getCartById(principal.getName()).orElse(new Cart(principal.getName()));
            if (anonCartUUID != null) {
                Cart anonCart = cartRepository.getCartById(anonCartUUID).orElse(null);
                if (anonCart != null && !anonCart.getCurrentCart().isEmpty()) {
                    anonCart.getCurrentCart().values().forEach(e -> userCart.addProduct(e.getProduct(), e.getQnt()));
                    cartRepository.delete(anonCart);
                }
            }
            return userCart;
        }
    }

    private String getAnonCartCookieValue() {
        /*RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
            Cookie cookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equalsIgnoreCase(CookieFilter.ANON_CART_COOKIE_NAME))
                    .findFirst()
                    .orElse(null);
            return cookie == null ? null : cookie.getValue();
        }*/
        return null;
    }

    @TrackExecutionTime
    public CartDto addToCart(Long id, Integer qnt) {
        Cart cart = getCartForCurrentUser();
        productService.getProductById(id).ifPresent(product -> cart.addProduct(cartMapper.productToCartPositionProduct(product), qnt));
        System.out.println("Cart size after adding = " + cart.getCurrentCart().size());
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
