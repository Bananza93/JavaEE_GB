package ru.geekbrains.lesson7.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.mapper.ProductMapper;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.ProductSearchService;
import ru.geekbrains.lesson7.service.ProductService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;
    private final ProductMapper productMapper;

    public ProductController(@Qualifier(value = "productServiceWithCache") ProductService productService, ProductSearchService productSearchService, ProductMapper productMapper) {
        this.productService = productService;
        this.productSearchService = productSearchService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = Product.STR_MIN_PRICE) BigDecimal minPrice,
                                 @RequestParam(defaultValue = Product.STR_MAX_PRICE) BigDecimal maxPrice,
                                 @PageableDefault(size = 8) Pageable pageable) {
        Page<ProductDto> productsDto = productService.getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable).map(productMapper::productToProductDto);
        model.addAttribute("products", productsDto);
        return "product_list";
    }

    @GetMapping("/info/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        return "product_info";
    }

    @GetMapping("/search")
    public String getSearchProductsResult(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("products", productSearchService.searchProducts(search).stream().map(productMapper::productToProductDto).toList());
        model.addAttribute("searchString", search);
        return "search_products_result";
    }
}
