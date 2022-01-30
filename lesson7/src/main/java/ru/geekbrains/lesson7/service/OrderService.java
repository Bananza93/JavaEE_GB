package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.model.Cart;
import ru.geekbrains.lesson7.model.CartPosition;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.OrderItem;
import ru.geekbrains.lesson7.model.OrderItemId;
import ru.geekbrains.lesson7.model.OrderStatus;
import ru.geekbrains.lesson7.repository.OrderRepository;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private Map<String, OrderStatus> orderStatusCache;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @PostConstruct
    private void loadOrderStatusCache() {
        List<OrderStatus> statuses = orderRepository.getOrderStatuses();
        orderStatusCache = statuses.stream().collect(Collectors.toMap(OrderStatus::getCode, Function.identity()));
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
        order.setOrderStatus(orderStatusCache.get("notPaid"));
        orderRepository.save(order);
    }
}
