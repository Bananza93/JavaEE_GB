package ru.geekbrains.lesson7.controller;

import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.dto.ProductDto;
import ru.geekbrains.lesson7.mapper.OrderMapper;
import ru.geekbrains.lesson7.mapper.ProductMapper;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.service.CategoryService;
import ru.geekbrains.lesson7.service.OrderService;
import ru.geekbrains.lesson7.service.ProductService;
import ru.geekbrains.lesson7.service.StatisticService;
import ru.geekbrains.lesson7.service.StorageService;
import ru.geekbrains.lesson7.service.UserService;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final StatisticService statisticService;
    private final OrderService orderService;
    private final StorageService storageService;

    public AdminPanelController(UserService userService,
                                @Qualifier(value = "productService") ProductService productService,
                                CategoryService categoryService,
                                ProductMapper productMapper,
                                OrderMapper orderMapper,
                                StatisticService statisticService,
                                OrderService orderService,
                                StorageService storageService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.orderMapper = orderMapper;
        this.statisticService = statisticService;
        this.orderService = orderService;
        this.storageService = storageService;
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
    public String getAddNewProductForm(Model model) {
        model.addAttribute("productDto", ProductDto.builder().build());
        model.addAttribute("categories", categoryService.getAllNames());
        return "admin/new_product_form";
    }

    @PostMapping("/products/add")
    public String addNewProduct(@Valid ProductDto productDto, @RequestParam MultipartFile image, Model model) {
        storageService.store(image);
        productDto.setImageURL("/media/" + image.getOriginalFilename());
        productService.addProduct(productMapper.productDtoToProduct(productDto));
        return getAddNewProductForm(model);
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String getEditProductForm(@PathVariable Long id, Model model) {
        var product = productService.joinFullCharacteristicListToProduct(productService.getProductById(id).orElse(null));
        model.addAttribute("product", productMapper.productToProductDto(product));
        model.addAttribute("categories", categoryService.getAllNames());
        return "admin/edit_product_form";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid @ModelAttribute ProductDto productDto, @RequestParam MultipartFile image) {
        storageService.store(image);
        productDto.setImageURL("/media/" + image.getOriginalFilename());
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
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList()));
        model.addAttribute("order", OrderDto.builder().build());
        model.addAttribute("statusMap", orderService.getOrderStatusCache());
        return "/admin/processed_orders";
    }

    @PostMapping("/orders/{id}/changeStatus")
    public String changeOrderStatus(@PathVariable Long id, OrderDto dto) {
        orderService.changeOrderStatus(id, dto.getStatus().getCode());
        return "redirect:/admin/orders";
    }
}
