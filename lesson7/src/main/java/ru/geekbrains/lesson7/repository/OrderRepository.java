package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.AppUser;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from OrderStatus os")
    List<OrderStatus> getOrderStatuses();

    @Query("from Order o JOIN FETCH o.orderStatus where o.lastChangeStatusEndDate is null")
    List<Order> getProcessedOrders();

    @Query("FROM Order o JOIN FETCH o.orderStatus JOIN FETCH o.userPersonalData JOIN FETCH o.deliveryAddress WHERE o.user = :user AND o.lastChangeStatusEndDate IS NULL ORDER BY o.createdAt DESC")
    List<Order> getAllUserOrders(AppUser user);

    @Query("FROM Order o JOIN FETCH o.orderStatus JOIN FETCH o.userPersonalData JOIN FETCH o.deliveryAddress WHERE o.user = :user AND o.id = :orderId AND o.lastChangeStatusEndDate IS NULL")
    Order getUserOrder(AppUser user, Long orderId);

    @Query("from Order o JOIN FETCH o.orderStatus where o.lastChangeStatusEndDate is null and o.manager is null order by o.createdAt")
    List<Order> getAllUnprocessedOrders();

    @Query("from Order o JOIN FETCH o.orderStatus where o.lastChangeStatusEndDate is null and o.manager = :manager order by o.createdAt")
    List<Order> getAllProcessedByCurrentManagerOrders(AppUser manager);

    @Query("FROM Order o JOIN FETCH o.orderStatus JOIN FETCH o.manager JOIN FETCH o.userPersonalData JOIN FETCH o.deliveryAddress WHERE o.id = :orderId AND o.lastChangeStatusEndDate IS NULL")
    Order getOrderById(Long orderId);

    void closeCurrentOrderStatus(Long orderId);

    void insertNewOrderStatus(Long orderId, Long orderStatusId);
}
