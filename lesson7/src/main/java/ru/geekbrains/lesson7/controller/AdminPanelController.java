package ru.geekbrains.lesson7.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.mapper.OrderMapper;
import ru.geekbrains.lesson7.mapper.ProductMapper;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.OrderService;
import ru.geekbrains.lesson7.service.ProductService;
import ru.geekbrains.lesson7.service.StatisticService;
import ru.geekbrains.lesson7.service.UserService;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService userService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final StatisticService statisticService;
    private final OrderService orderService;

    public AdminPanelController(UserService userService,
                                ProductService productService,
                                ProductMapper productMapper,
                                StatisticService statisticService,
                                OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.productMapper = productMapper;
        this.statisticService = statisticService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersDto());
        return "admin/user_list";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model,
                                 @RequestParam(defaultValue = Product.STR_MIN_PRICE) BigDecimal minPrice,
                                 @RequestParam(defaultValue = Product.STR_MAX_PRICE) BigDecimal maxPrice,
                                 @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("products", productService.getAllProductsWithinPriceRangeByPage(minPrice, maxPrice, pageable));
        return "admin/product_list";
    }

    @GetMapping("/products/add")
    public String getAddNewProductForm() {
        return "admin/new_product_form";
    }

    @PostMapping("/products/add")
    public String addNewProduct(@Valid ProductDto productDto) {
        productService.addProduct(productMapper.productDtoToProduct(productDto));
        return "admin/new_product_form";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String getEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productMapper.productToProductDto(productService.getProductById(id).orElse(null)));
        return "admin/edit_product_form";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid ProductDto productDto) {
        productService.editProduct(id, productMapper.productDtoToProduct(productDto));
        return "redirect:/admin/products/info/{id}";
    }

    @GetMapping("products/info/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        return "admin/product_info";
    }

    @GetMapping("/statistic")
    public String getStatistic(Model model) {
        model.addAttribute("stat", statisticService.getStatistic());
        return "/admin/statistic";
    }

    @GetMapping("/orders")
    public String getProcessedOrders(@ModelAttribute OrderDto order, BindingResult result, Model model) {
        model.addAttribute("orders", orderService.getAllProcessedOrders()
                .stream()
                .map(OrderMapper::orderToOrderDto)
                .collect(Collectors.toList()));
        model.addAttribute("order", new OrderDto());
        model.addAttribute("statusMap", orderService.getOrderStatusCache());
        return "/admin/processed_orders";
    }

    @PostMapping("/orders/{id}/changeStatus")
    public String changeOrderStatus(@PathVariable Long id, OrderDto dto) {
        orderService.changeOrderStatus(id, dto.getStatusCode());
        return "redirect:/admin/orders";
    }
}
