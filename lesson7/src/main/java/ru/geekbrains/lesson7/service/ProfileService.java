package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.model.AppUser;
import ru.geekbrains.lesson7.model.Order;

import java.security.Principal;
import java.util.List;

@Service
public class ProfileService {

    private final UserService userService;
    private final OrderService orderService;

    public ProfileService(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    public List<Order> getUserOrders(Principal principal) {
        AppUser user = userService.getUserByEmail(principal.getName());
        return orderService.getUserOrders(user);
    }

    public Order getUserOrderDetails(Principal principal, Long orderId) {
        AppUser user = userService.getUserByEmail(principal.getName());
        return orderService.getUserOrder(user, orderId);
    }
}
