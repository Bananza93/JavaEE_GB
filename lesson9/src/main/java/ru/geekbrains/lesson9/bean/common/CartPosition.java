package ru.geekbrains.lesson9.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.lesson9.model.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPosition {
    private Product product;
    private Integer qnt;

    public void increaseQnt(Integer value) {
        qnt += value;
    }

    public void decreaseQnt(Integer value) {
        qnt -= value;
    }

    public BigDecimal getPositionPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(qnt));
    }
}
