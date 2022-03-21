package ru.geekbrains.lesson7.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(onlyExplicitlyIncluded = true)
@RedisHash(value = "Cart", timeToLive = 86400)
public class Cart {

    private String id;
    private Integer productCount;
    private List<CartPosition> currentCart;
    private BigDecimal sumPrice;

    public Cart() {
        productCount = 0;
        currentCart = new ArrayList<>();
        sumPrice = BigDecimal.ZERO;
    }

    public Cart(String id) {
        this();
        this.id = id;
    }

    public void addProduct(CartPositionProduct product, Integer qnt) {
        currentCart.stream()
                .filter(e -> e.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(e -> e.increaseQnt(qnt), () -> currentCart.add(new CartPosition(product, qnt)));
        recalculateAfterChange();
    }

    public void removeProduct(Long productId, Integer qnt) {
        currentCart.stream().filter(e -> e.getProduct().getId().equals(productId)).findFirst().ifPresent(item -> {
            item.decreaseQnt(qnt);
            if (item.getQnt() <= 0) currentCart.remove(item);
        });
        recalculateAfterChange();
    }

    private void recalculateAfterChange() {
        recalculateProductCount();
        recalculateSumPrice();
    }

    private void recalculateProductCount() {
        productCount = currentCart.stream()
                .mapToInt(CartPosition::getQnt)
                .sum();
    }

    private void recalculateSumPrice() {
        sumPrice = currentCart.stream()
                .map(CartPosition::getPositionPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
