package ru.geekbrains.lesson9.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.lesson9.bean.Cart;
import ru.geekbrains.lesson9.dto.CartDto;
import ru.geekbrains.lesson9.dto.CartPositionDto;
import ru.geekbrains.lesson9.mapper.CartMapper;
import ru.geekbrains.lesson9.model.Product;

import javax.annotation.Resource;

@Service
public class CartService {

    private final ProductService productService;

    @Resource(name = "cart")
    Cart cart;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public CartDto getCart() {
        return CartMapper.cartToCartDto(cart);
    }

    public void addToCart(CartPositionDto cartPositionDto) {
        if (!cart.getCurrentCart().containsKey(cartPositionDto.getProductId())) {
            Product product = productService.getProductById(cartPositionDto.getProductId());
            if (product == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id = " + cartPositionDto.getProductId() + " doesn't exists");
            cart.addProduct(product, cartPositionDto.getQnt());
        } else {
            cart.addProduct(cartPositionDto.getProductId(), cartPositionDto.getQnt());
        }
    }

    public void removeFromCart(CartPositionDto cartPositionDto) {
        if (!cart.getCurrentCart().containsKey(cartPositionDto.getProductId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id = " + cartPositionDto.getProductId() + " not found into cart");
        cart.removeProduct(cartPositionDto.getProductId(), cartPositionDto.getQnt());
    }
}
