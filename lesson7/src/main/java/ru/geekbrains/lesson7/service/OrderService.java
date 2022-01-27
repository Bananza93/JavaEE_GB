package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.model.*;
import ru.geekbrains.lesson7.repository.OrderRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public void makeOrder(Cart cart, Principal principal) {
        Order order = new Order();
        order.setUser(principal == null ? null : userService.getUserByUsername(principal.getName()));
        order.setTotalPrice(cart.getSumPrice());
        List<OrderItem> items = new ArrayList<>();
        for(CartPosition cp : cart.getCurrentCart().values()) {
            OrderItem item = new OrderItem();
            item.setId(new OrderItemId(order, cp.getProduct()));
            item.setPrice(cp.getPositionPrice());
            item.setQuantity(cp.getQnt());
            items.add(item);
        }
        order.setOrderItems(items);
        orderRepository.save(order);
    }
}
