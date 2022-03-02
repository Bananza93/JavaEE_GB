package ru.geekbrains.lesson7.mapper;

import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.model.Order;

import java.time.format.DateTimeFormatter;

public class OrderMapper {

    public static OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setStatusCode(order.getOrderStatus().get(0).getCode());
        orderDto.setCreatedAt(order.getCreatedAt() == null ? null : order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));
        orderDto.setLastChangeStatusDate(order.getLastChangeStatusStartDate() == null ? null : order.getLastChangeStatusStartDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));
        return orderDto;
    }
}
