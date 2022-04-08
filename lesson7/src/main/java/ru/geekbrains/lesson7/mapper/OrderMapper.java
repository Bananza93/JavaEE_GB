package ru.geekbrains.lesson7.mapper;

import org.springframework.stereotype.Component;
import ru.geekbrains.lesson7.dto.OrderDetailsDto;
import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.dto.OrderItemDto;
import ru.geekbrains.lesson7.dto.OrderStatusDto;
import ru.geekbrains.lesson7.model.DeliveryAddress;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.model.OrderItem;

import java.time.format.DateTimeFormatter;

@Component
public class OrderMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderDto orderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .status(new OrderStatusDto(order.getOrderStatus().get(0).getCode(), order.getOrderStatus().get(0).getDescription()))
                .managerEmail(order.getManager() == null ? null : order.getManager().getEmail())
                .createdAt(order.getCreatedAt() == null ? null : order.getCreatedAt().format(DATE_TIME_FORMATTER))
                .lastChangeStatusDate(order.getLastChangeStatusStartDate() == null ? null : order.getLastChangeStatusStartDate().format(DATE_TIME_FORMATTER))
                .build();
    }

    public OrderDetailsDto orderToOrderDetailsDto(Order order) {
        return OrderDetailsDto.builder()
                .id(order.getId())
                .items(order.getOrderItems().stream().map(this::orderItemToOrderItemDto).toList())
                .status(new OrderStatusDto(order.getOrderStatus().get(0).getCode(), order.getOrderStatus().get(0).getDescription()))
                .managerEmail(order.getManager() == null ? null : order.getManager().getEmail())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt() == null ? null : order.getCreatedAt().format(DATE_TIME_FORMATTER))
                .deliveryAddress(deliveryAddressToString(order.getDeliveryAddress()))
                .build();
    }

    private OrderItemDto orderItemToOrderItemDto(OrderItem item) {
        return OrderItemDto.builder()
                .product(productMapper.productToProductDto(item.getId().getProduct()))
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

    private String deliveryAddressToString(DeliveryAddress address) {
        StringBuilder sb = new StringBuilder();
        sb.append(address.getPostcode());
        sb.append(", г. ")
                .append(address.getCity());
        sb.append(", ул. ")
                .append(address.getStreet())
                .append(" ")
                .append(address.getBuilding());
        if (address.getEntrance() != null) {
            sb.append(", п. ")
                    .append(address.getEntrance());
        }
        if (address.getFloor() != null) {
            sb.append(", эт. ")
                    .append(address.getFloor());
        }
        sb.append(", кв. ")
                .append(address.getApartment());
        return sb.toString();
    }
}
