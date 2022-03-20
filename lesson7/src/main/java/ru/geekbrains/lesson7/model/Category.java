package ru.geekbrains.lesson7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column(name = "image")
    private String imageURL;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> product;

    @ManyToMany
    @JoinTable(
            name = "product_attributes_product_categories",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    )
    private List<ProductAttribute> attributes;
}
