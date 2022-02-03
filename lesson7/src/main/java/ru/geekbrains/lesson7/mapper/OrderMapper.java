package ru.geekbrains.lesson7.mapper;

import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.model.Order;

public class OrderMapper {

    public static OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }
}
