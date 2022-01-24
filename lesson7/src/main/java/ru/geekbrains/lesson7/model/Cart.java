package ru.geekbrains.lesson7.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Cart {

    private Map<Long, CartPosition> currentCart;
    private BigDecimal sumPrice;

    public Cart() {
        currentCart = new LinkedHashMap<>();
        sumPrice = BigDecimal.ZERO;
    }

    public void addProduct(Product product, Integer qnt) {
        currentCart.merge(product.getId(), new CartPosition(product, qnt), (oldItem, newItem) -> {
            oldItem.increaseQnt(qnt);
            return oldItem;
        });
        recalculateSumPrice();
    }

    public void removeProduct(Long productId, Integer qnt) {
        CartPosition position = currentCart.get(productId);
        position.decreaseQnt(qnt);
        if (position.getQnt() <= 0) currentCart.remove(productId);
        recalculateSumPrice();
    }

    private void recalculateSumPrice() {
        sumPrice = currentCart.values().stream()
                .map(CartPosition::getPositionPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
