package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from OrderStatus os")
    List<OrderStatus> getOrderStatuses();

    @Query("from Order o where o.lastChangeStatusEndDate is null")
    List<Order> getProcessedOrders();

    void closeCurrentOrderStatus(Long orderId);

    void insertNewOrderStatus(Long orderId, Long orderStatusId);
}
