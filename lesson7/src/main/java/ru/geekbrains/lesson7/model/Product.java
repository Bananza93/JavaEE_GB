package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.redis.core.RedisHash;

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
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name="products")
@SecondaryTable(name = "product_price_histories", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id"))
@DynamicInsert
@DynamicUpdate
public class Product {

    public static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    public static final BigDecimal MAX_PRICE = BigDecimal.valueOf(9_999_999.99);
    public static final String STR_MIN_PRICE = "0.0";
    public static final String STR_MAX_PRICE = "9999999.99";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "image")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "price", table = "product_price_histories", nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_attribute_values",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attr_value_id", referencedColumnName = "id")
    )
    private List<ProductAttributeValue> productCharacteristics;

}
