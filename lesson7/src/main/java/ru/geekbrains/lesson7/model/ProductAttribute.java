package ru.geekbrains.lesson7.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "attributes")
public class ProductAttribute {

    @Id
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "attributes")
    private List<Category> categories;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL)
    private List<ProductAttributeValue> attributeValues;

}
