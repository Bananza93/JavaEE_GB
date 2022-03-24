package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.mapper.OrderMapper;
import ru.geekbrains.lesson7.model.AppUser;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.Role;
import ru.geekbrains.lesson7.repository.OrderRepository;
import ru.geekbrains.lesson7.repository.UserRepository;
import ru.geekbrains.lesson7.service.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/management")
public class ManagementController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public ManagementController(OrderService orderService, OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/unprocessedOrders")
    public String getUnprocessedOrdersManagementPage(Model model) {
        List<OrderDto> orders = orderRepository.getAllUnprocessedOrders()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .toList();

        model.addAttribute("orders", orders);
        return "unprocessed_orders_management";
    }

    @PostMapping("/unprocessedOrders/{id}/submit")
    public RedirectView submitOrder(@PathVariable(name = "id") Long orderId, Principal principal, final RedirectAttributes attributes) {
        AppUser user = userRepository.findUserByEmail(principal.getName()).get();
        Order order = orderRepository.getById(orderId);

        if (order.getManager() == null) {
            orderService.changeManager(order, user);
            return new RedirectView("/management/processedOrders", true);
        } else {
            attributes.addFlashAttribute("alreadySubmit", new IllegalStateException("Manager " + order.getManager().getEmail() + " already submit this order"));
            return new RedirectView("/management/unprocessedOrders", true);
        }
    }

    @GetMapping("/processedOrders")
    public String getProcessedOrdersManagementPage(Principal principal, Model model) {
        AppUser user = userRepository.findUserByEmail(principal.getName()).get();
        List<OrderDto> orders = orderRepository.getAllProcessedByCurrentManagerOrders(user)
                .stream()
                .map(orderMapper::orderToOrderDto)
                .toList();

        model.addAttribute("orders", orders);
        return "processed_orders_management";
    }

    @GetMapping("/order/{id}")
    public String getOrderPage(@PathVariable(name = "id") Long orderId, Model model) {
        Order order = orderRepository.getOrderById(orderId);

        model.addAttribute("order", orderMapper.orderToOrderDetailsDto(order));
        model.addAttribute("statusMap", orderService.getOrderStatusCache());
        return "order_management";
    }

    @PostMapping("/order/{id}/changeStatus")
    public String changeOrderStatus(@PathVariable(name = "id") Long orderId, OrderDto dto, Principal principal) {
        AppUser user = userRepository.findUserByEmail(principal.getName()).get();
        Order order = orderRepository.getOrderById(orderId);

        if (user.getRoles().stream().map(Role::getName).anyMatch(n -> n.equals("ADMIN")) || (order.getManager() != null && order.getManager().getEmail().equalsIgnoreCase(principal.getName()))) {
            orderService.changeOrderStatus(orderId, dto.getStatus().getCode());
        }
        return "redirect:/management/order/{id}";
    }
}
