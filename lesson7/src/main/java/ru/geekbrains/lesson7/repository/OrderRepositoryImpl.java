package ru.geekbrains.lesson7.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class OrderRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private OrderRepository orderRepository;

    @Transactional
    public void closeCurrentOrderStatus(Long orderId) {
        String sql = "UPDATE order_status_histories SET end_date = NOW() WHERE order_id = ? AND end_date IS NULL";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, orderId);
        query.executeUpdate();
    }

    @Transactional
    public void insertNewOrderStatus(Long orderId, Long orderStatusId) {
        String sql = "INSERT INTO order_status_histories VALUES (?, ?, now(), null)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, orderId);
        query.setParameter(2, orderStatusId);
        query.executeUpdate();
    }
}
