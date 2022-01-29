package ru.geekbrains.lesson7.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "order_statuses")
public class OrderStatus {

    @Id
    @Column
    private Long id;

    @Column
    private String code;

    @Column
    private String description;

    @OneToMany(mappedBy = "orderStatus", cascade = CascadeType.ALL)
    List<Order> orders;
}
