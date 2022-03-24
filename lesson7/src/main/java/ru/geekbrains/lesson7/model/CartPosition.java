package ru.geekbrains.lesson7.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class CartPosition {

    private CartPositionProduct product;
    private Integer qnt;
    private BigDecimal positionPrice;

    public CartPosition(CartPositionProduct product, Integer qnt) {
        this.product = product;
        this.qnt = qnt;
        calculatePositionPrice();
    }

    public void increaseQnt(Integer value) {
        qnt += value;
        calculatePositionPrice();
    }

    public void decreaseQnt(Integer value) {
        qnt -= value;
        calculatePositionPrice();
    }

    private void calculatePositionPrice() {
        positionPrice = product.getPrice().multiply(BigDecimal.valueOf(qnt));
    }
}
