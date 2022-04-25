package ru.geekbrains.lesson7.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.model.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service(value = "productServiceWithCache")
public class ProductServiceImplWithCache implements ProductService {

    private final ProductService productService;
    private Map<Long, Product> productMap;

    public ProductServiceImplWithCache(@Qualifier(value = "productService") ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    void init() {
        productMap = getAllProducts()
                .stream()
                .collect(Collectors.toConcurrentMap(Product::getId, Function.identity()));
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        if (productMap.containsKey(id)) {
            return Optional.ofNullable(productMap.get(id));
        } else {
            var product = productService.getProductById(id);
            productMap.put(product.get().getId(), product.get());
            return product;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public Page<Product> getAllProductsWithinPriceRangeByPage(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productService.getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable);
    }

    @Override
    public List<Product> getAllProductsWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productService.getAllProductsWithPriceRange(minPrice, maxPrice);
    }

    @Override
    public void addProduct(Product product) {
        productService.addProduct(product);
        productMap.put(product.getId(), product);
    }

    @Override
    public void deleteProduct(Long id) {
        productMap.remove(id);
        productService.deleteProduct(id);
    }

    @Override
    public void editProduct(Long id, Product product) {
        productService.editProduct(id, product);
        productMap.put(id, product);
    }

    @Override
    public Product joinFullCharacteristicListToProduct(Product product) {
        return productService.joinFullCharacteristicListToProduct(product);
    }
}
