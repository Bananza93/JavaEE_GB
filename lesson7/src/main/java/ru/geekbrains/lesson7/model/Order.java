package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
@SecondaryTable(name = "order_payments", pkJoinColumns = @PrimaryKeyJoinColumn(name = "order_id"))
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "price", table = "order_payments", nullable = false)
    private BigDecimal totalPrice;
}
