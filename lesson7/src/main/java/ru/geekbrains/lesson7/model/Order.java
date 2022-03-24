package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "orders")
@SecondaryTables({
        @SecondaryTable(name = "order_payments", pkJoinColumns = @PrimaryKeyJoinColumn(name = "order_id")),
        @SecondaryTable(name = "order_status_histories", pkJoinColumns = @PrimaryKeyJoinColumn(name = "order_id"))
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @Column(name = "contact_email")
    private String contactEmail;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "price", table = "order_payments", nullable = false)
    private BigDecimal totalPrice;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_status_histories",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "status_id", referencedColumnName = "id")
    )
    @WhereJoinTable(clause = "end_date is null")
    private List<OrderStatus> orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_pers_data_id", referencedColumnName = "id")
    private UserPersonalData userPersonalData;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "deli_addr_id", referencedColumnName = "id")
    private DeliveryAddress deliveryAddress;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "start_date", table = "order_status_histories")
    private LocalDateTime lastChangeStatusStartDate;

    @Column(name = "end_date", table = "order_status_histories")
    private LocalDateTime lastChangeStatusEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private AppUser manager;
}
