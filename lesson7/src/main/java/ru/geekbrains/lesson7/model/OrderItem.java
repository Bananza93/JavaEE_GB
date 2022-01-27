package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemId id;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal price;

}
