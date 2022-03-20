package ru.geekbrains.lesson7.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString(onlyExplicitlyIncluded = true)
@RedisHash(value = "Cart", timeToLive = 86400)
public class Cart {

    private String id;
    private Integer productCount;
    private Map<Long, CartPosition> currentCart;
    private BigDecimal sumPrice;

    public Cart() {
        productCount = 0;
        currentCart = new LinkedHashMap<>();
        sumPrice = BigDecimal.ZERO;
    }

    public Cart(String id) {
        this();
        this.id = id;
    }

    public void addProduct(CartPositionProduct product, Integer qnt) {
        currentCart.merge(product.getId(), new CartPosition(product, qnt), (oldItem, newItem) -> {
            oldItem.increaseQnt(qnt);
            return oldItem;
        });
        recalculateAfterChange();
    }

    public void removeProduct(Long productId, Integer qnt) {
        CartPosition position = currentCart.get(productId);
        position.decreaseQnt(qnt);
        if (position.getQnt() <= 0) currentCart.remove(productId);
        recalculateAfterChange();
    }

    private void recalculateAfterChange() {
        recalculateProductCount();
        recalculateSumPrice();
    }

    private void recalculateProductCount() {
        productCount = currentCart.values().stream()
                .mapToInt(CartPosition::getQnt)
                .sum();
    }

    private void recalculateSumPrice() {
        sumPrice = currentCart.values().stream()
                .map(CartPosition::getPositionPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
