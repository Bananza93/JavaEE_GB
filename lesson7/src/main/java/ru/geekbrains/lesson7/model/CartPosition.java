package ru.geekbrains.lesson7.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartPosition {
    private Product product;
    private Integer qnt;
    private BigDecimal positionPrice;

    public CartPosition(Product product, Integer qnt) {
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
