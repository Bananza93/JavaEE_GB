package ru.geekbrains.lesson7.model;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString(onlyExplicitlyIncluded = true)
public class Cart {

    private Integer productCount;
    private Map<Long, CartPosition> currentCart;
    private BigDecimal sumPrice;

    public Cart() {
        productCount = 0;
        currentCart = new LinkedHashMap<>();
        sumPrice = BigDecimal.ZERO;
    }

    public void addProduct(Product product, Integer qnt) {
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
