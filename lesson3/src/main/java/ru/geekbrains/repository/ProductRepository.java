package ru.geekbrains.repository;

import org.springframework.stereotype.Component;
import ru.geekbrains.dto.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {

    private List<Product> productList;

    @PostConstruct
    public void init() {
        productList = new ArrayList<>();
        productList.add(new Product(1, "PSU", 100.0));
        productList.add(new Product(2, "Motherboard", 200.0));
        productList.add(new Product(3, "Memory RAM", 300.0));
        productList.add(new Product(4, "CPU", 400.0));
        productList.add(new Product(5, "Graphic Card", 500.0));
    }

    public List<Product> getAllProducts() {
        return List.copyOf(productList);
    }

    public Product getProductById(int id) {
        return productList.stream().filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addNewProduct(Product product) {
        productList.add(product);
    }
}
