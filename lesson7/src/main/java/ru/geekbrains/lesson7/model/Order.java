package ru.geekbrains.lesson7.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "id.order")
    private List<OrderItem> orderItems;

    @Column(name = "price", table = "order_payments", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_status_histories",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "status_id", referencedColumnName = "id")
    )
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pers_data_id", referencedColumnName = "id")
    private UserPersonalData userPersonalData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deli_addr_id", referencedColumnName = "id")
    private DeliveryAddress deliveryAddress;
}
