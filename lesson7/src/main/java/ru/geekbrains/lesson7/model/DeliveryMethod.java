package ru.geekbrains.lesson7.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "delivery_methods")
public class DeliveryMethod {

    @Id
    @Column
    private Long id;

    @Column
    private String code;

    @Column
    private String description;
}