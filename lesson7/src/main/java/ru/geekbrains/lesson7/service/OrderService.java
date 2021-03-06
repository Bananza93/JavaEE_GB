package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
import ru.geekbrains.lesson7.dto.UserCheckoutDto;
import ru.geekbrains.lesson7.mapper.UserMapper;
import ru.geekbrains.lesson7.model.AppUser;
import ru.geekbrains.lesson7.model.Cart;
import ru.geekbrains.lesson7.model.CartPosition;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.OrderItem;
import ru.geekbrains.lesson7.model.OrderItemId;
import ru.geekbrains.lesson7.model.OrderStatus;
import ru.geekbrains.lesson7.repository.OrderRepository;
import ru.geekbrains.lesson7.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.geekbrains.lesson7.model.EmailType.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartService cartService;
    private final EmailService emailService;
    private Map<String, OrderStatus> orderStatusCache;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserService userService,
                        CartService cartService,
                        EmailService emailService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.emailService = emailService;
    }

    @PostConstruct
    private void loadOrderStatusCache() {
        List<OrderStatus> statuses = orderRepository.getOrderStatuses();
        orderStatusCache = statuses.stream().collect(Collectors.toMap(OrderStatus::getCode, Function.identity()));
    }

    public Map<String, OrderStatus> getOrderStatusCache() {
        return orderStatusCache;
    }

    @TrackExecutionTime
    @Transactional
    public Order makeOrder(UserCheckoutDto ucd, Principal principal) {
        Cart cart = cartService.getCartForCurrentUser();

        if (cart.getCurrentCart().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        AppUser user = principal == null ? UserMapper.userCheckoutDtoToUser(ucd)
                : UserMapper.userCheckoutDtoToUser(ucd, userService.getUserByEmail(principal.getName()));
        if (user.getId() != null) order.setUser(user);

        order.setContactEmail(ucd.getEmail());
        order.setUserPersonalData(user.getPersonalData());
        order.setDeliveryAddress(user.getDeliveryAddress());
        order.setTotalPrice(cart.getSumPrice());

        List<OrderItem> items = new ArrayList<>();
        for(CartPosition cp : cart.getCurrentCart()) {
            OrderItem item = new OrderItem();
            item.setId(new OrderItemId(order, productRepository.getById(cp.getProduct().getId())));
            item.setPrice(cp.getPositionPrice());
            item.setQuantity(cp.getQnt());
            items.add(item);
        }
        order.setOrderItems(items);

        order.setOrderStatus(List.of(orderStatusCache.get("notPaid")));
        orderRepository.save(order);
        cartService.removeCartForCurrentUser();

        String userEmail = principal == null ? order.getContactEmail() : order.getUser().getEmail();
        List<String> managerEmails = userService.getActiveManagers().stream().map(AppUser::getEmail).collect(Collectors.toList());

        emailService.sendEmail(USER_ORDER_CREATED, Map.of("orderId", order.getId(), "orderPrice", order.getTotalPrice()), List.of(userEmail));
        emailService.sendEmail(MANAGER_ORDER_CREATED, Map.of("orderId", order.getId(), "orderPrice", order.getTotalPrice()), managerEmails);

        return order;
    }

    public List<Order> getUserOrders(AppUser user) {
        return orderRepository.getAllUserOrders(user);
    }

    public Order getUserOrder(AppUser user, Long orderId) {
        return orderRepository.getUserOrder(user, orderId);
    }

    public List<Order> getAllProcessedOrders() {
        return orderRepository.getProcessedOrders();
    }

    @Transactional
    public void changeOrderStatus(Long orderId, String newStatusCode) {
        orderRepository.closeCurrentOrderStatus(orderId);
        orderRepository.insertNewOrderStatus(orderId, orderStatusCache.get(newStatusCode).getId());
    }

    @Transactional
    public void changeManager(Order order, AppUser manager) {
        order.setManager(manager);
        orderRepository.save(order);
    }
}
