package ru.geekbrains.lesson9.bean;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.lesson9.bean.common.CartPosition;
import ru.geekbrains.lesson9.model.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("cart")
@SessionScope
@Data
public class Cart {

    private Map<Long, CartPosition> currentCart;
    private BigDecimal sumPrice;

    public Cart() {
        currentCart = new LinkedHashMap<>();
        sumPrice = BigDecimal.ZERO;
    }

    public void addProduct(Product product, Integer qnt) {
        currentCart.put(product.getId(), new CartPosition(product, qnt));
        recalculateSumPrice();
    }

    public void addProduct(Long productId, Integer qnt) {
        CartPosition position = currentCart.get(productId);
        position.increaseQnt(qnt);
        recalculateSumPrice();
    }

    public void removeProduct(Long productId, Integer qnt) {
        CartPosition position = currentCart.get(productId);
        position.decreaseQnt(qnt);
        if (position.getQnt() <= 0) currentCart.remove(productId);
        recalculateSumPrice();
    }

    private void recalculateSumPrice() {
        sumPrice = BigDecimal.ZERO;
        for (CartPosition position : currentCart.values()) {
            sumPrice = sumPrice.add(position.getPositionPrice());
        }
    }
}
