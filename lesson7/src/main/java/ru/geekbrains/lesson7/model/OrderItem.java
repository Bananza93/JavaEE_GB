package ru.geekbrains.lesson7.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
