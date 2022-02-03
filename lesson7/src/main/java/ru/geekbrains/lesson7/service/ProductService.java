package ru.geekbrains.lesson7.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> getAllProductsWithinPriceRangeByPage(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (minPrice.compareTo(Product.MIN_PRICE) < 0 || minPrice.compareTo(Product.MAX_PRICE) > 0)
            minPrice = Product.MIN_PRICE;
        if (maxPrice.compareTo(Product.MIN_PRICE) < 0 || maxPrice.compareTo(Product.MAX_PRICE) > 0)
            maxPrice = Product.MAX_PRICE;
        if (maxPrice.compareTo(minPrice) < 0) {
            BigDecimal tmpMinPrice = minPrice;
            minPrice = maxPrice;
            maxPrice = tmpMinPrice;
        }
        return productRepository.findAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable);
    }

    public List<Product> getAllProductsWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, null).toList();
    }

    public void addProduct(Product product) {
        productRepository.saveAndFlush(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void editProduct(Long id, Product product) {
        Product sourceProduct = productRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException();});
        product.setId(sourceProduct.getId());
        product.setQuantity(sourceProduct.getQuantity());
        product.setIsAvailable(sourceProduct.getIsAvailable());
        productRepository.saveAndFlush(product);
    }
}
