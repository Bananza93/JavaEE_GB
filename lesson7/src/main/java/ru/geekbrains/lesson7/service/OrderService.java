package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.dto.UserCheckoutDto;
import ru.geekbrains.lesson7.mapper.UserMapper;
import ru.geekbrains.lesson7.model.CartPosition;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.OrderItem;
import ru.geekbrains.lesson7.model.OrderItemId;
import ru.geekbrains.lesson7.model.OrderStatus;
import ru.geekbrains.lesson7.model.User;
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
    private final CartService cartService;
    private Map<String, OrderStatus> orderStatusCache;

    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostConstruct
    private void loadOrderStatusCache() {
        List<OrderStatus> statuses = orderRepository.getOrderStatuses();
        orderStatusCache = statuses.stream().collect(Collectors.toMap(OrderStatus::getCode, Function.identity()));
    }

    @TrackExecutionTime
    @Transactional
    public Order makeOrder(UserCheckoutDto ucd, Principal principal) {
        if (cartService.getCart().getCurrentCart().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        Order order = new Order();
        User user = principal == null ? UserMapper.userCheckoutDtoToUser(ucd)
                : UserMapper.userCheckoutDtoToUser(ucd, userService.getUserByUsername(principal.getName()));
        if (user.getUsername() != null) order.setUser(user);

        order.setUserPersonalData(user.getPersonalData());
        order.setDeliveryAddress(user.getDeliveryAddress());

        order.setTotalPrice(cartService.getCart().getSumPrice());
        List<OrderItem> items = new ArrayList<>();
        for(CartPosition cp : cartService.getCart().getCurrentCart().values()) {
            OrderItem item = new OrderItem();
            item.setId(new OrderItemId(order, cp.getProduct()));
            item.setPrice(cp.getPositionPrice());
            item.setQuantity(cp.getQnt());
            items.add(item);
        }
        order.setOrderItems(items);
        order.setOrderStatus(orderStatusCache.get("notPaid"));
        orderRepository.save(order);
        cartService.init();
        return order;
    }
}
