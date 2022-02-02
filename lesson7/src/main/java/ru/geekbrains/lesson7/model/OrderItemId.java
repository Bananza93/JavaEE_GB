package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderItemId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Product product;
}
