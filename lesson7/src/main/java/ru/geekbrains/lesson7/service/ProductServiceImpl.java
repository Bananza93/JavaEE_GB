package ru.geekbrains.lesson7.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.model.ProductAttributeValue;
import ru.geekbrains.lesson7.repository.ProductRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductSearchService productSearchService;

    public ProductServiceImpl(ProductRepository productRepository, ProductSearchService productSearchService) {
        this.productRepository = productRepository;
        this.productSearchService = productSearchService;
    }

    @PostConstruct
    void indexingAllProductsForSearch() {
        var products = productRepository.findAll();
        productSearchService.indexProducts(products);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @TrackExecutionTime
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

    @TrackExecutionTime
    public List<Product> getAllProductsWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, null).toList();
    }

    @TrackExecutionTime
    public void addProduct(Product product) {
        productRepository.save(product);
        productSearchService.indexProduct(product);
    }

    @TrackExecutionTime
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        productSearchService.deleteProduct(id);
    }

    @TrackExecutionTime
    public void editProduct(Long id, Product product) {
        productRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
        productRepository.save(product);
        productSearchService.indexProduct(product);
    }

    public Product joinFullCharacteristicListToProduct(Product product) {
        if (product != null) {
            List<ProductAttributeValue> fullList = productRepository.getAllProductAttributes()
                    .stream()
                    .map(a -> new ProductAttributeValue(null, a, null))
                    .toList();

            for (ProductAttributeValue c : product.getProductCharacteristics()) {
                fullList.stream()
                        .filter(e -> Objects.equals(e.getAttribute().getId(), c.getAttribute().getId()))
                        .findFirst().ifPresent(fe -> {
                            fe.setId(c.getId());
                            fe.setValue(c.getValue());
                        });
            }
            product.setProductCharacteristics(fullList);
        }
        return product;
    }
}
