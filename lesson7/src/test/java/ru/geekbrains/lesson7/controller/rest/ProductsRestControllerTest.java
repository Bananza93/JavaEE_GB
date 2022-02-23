package ru.geekbrains.lesson7.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.lesson7.mapper.ProductMapper;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductsRestControllerTest {

    private ProductsRestController controller;

    private Product product1;
    private Product product2;
    private Product product3;

    private List<Product> productList1;
    private List<Product> productList2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product2 = new Product();
        product3 = new Product();

        product1.setPrice(BigDecimal.valueOf(100));
        product2.setPrice(BigDecimal.valueOf(200));
        product3.setPrice(BigDecimal.valueOf(300));

        productList1 = List.of(product1, product2);
        productList2 = List.of(product3);

        ProductService productService = mock(ProductService.class);
        ProductMapper productMapper = mock(ProductMapper.class);

        when(productService.getProductById(1L)).thenReturn(Optional.of(product1));
        when(productService.getProductById(3L)).thenReturn(Optional.of(product3));
        when(productService.getProductById(4L)).thenReturn(Optional.empty());
        when(productService.getProductById(-1L)).thenReturn(Optional.empty());

        when(productService.getAllProductsWithPriceRange(BigDecimal.valueOf(100), BigDecimal.valueOf(200)))
                .thenReturn(productList1);
        when(productService.getAllProductsWithPriceRange(BigDecimal.valueOf(300), BigDecimal.valueOf(400)))
                .thenReturn(productList2);

        controller = new ProductsRestController(productService, productMapper);
    }

    @Test
    void getProduct() {
        var product = controller.getProduct(1L);
        assertSame(product1, product);
        product = controller.getProduct(3L);
        assertSame(product3, product);
        product = controller.getProduct(4L);
        assertNull(product);
        product = controller.getProduct(-1L);
        assertNull(product);
    }

    @Test
    void getAllProducts() {
        var list = controller.getAllProducts(BigDecimal.valueOf(100), BigDecimal.valueOf(200));
        assertEquals(2, list.size());
        assertSame(productList1, list);
        list = controller.getAllProducts(BigDecimal.valueOf(300), BigDecimal.valueOf(400));
        assertEquals(1, list.size());
        assertSame(productList2, list);
    }

}