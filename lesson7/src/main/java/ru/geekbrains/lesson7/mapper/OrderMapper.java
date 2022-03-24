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
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setStatus(new OrderStatusDto(order.getOrderStatus().get(0).getCode(), order.getOrderStatus().get(0).getDescription()));
        orderDto.setManagerEmail(order.getManager() == null ? null : order.getManager().getEmail());
        orderDto.setCreatedAt(order.getCreatedAt() == null ? null : order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));
        orderDto.setLastChangeStatusDate(order.getLastChangeStatusStartDate() == null ? null : order.getLastChangeStatusStartDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));
        return orderDto;
    }

    public OrderDetailsDto orderToOrderDetailsDto(Order order) {
        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setId(order.getId());
        dto.setItems(order.getOrderItems().stream().map(this::orderItemToOrderItemDto).collect(Collectors.toList()));
        dto.setStatus(new OrderStatusDto(order.getOrderStatus().get(0).getCode(), order.getOrderStatus().get(0).getDescription()));
        dto.setManagerEmail(order.getManager() == null ? null : order.getManager().getEmail());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt() == null ? null : order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));
        dto.setDeliveryAddress(deliveryAddressToString(order.getDeliveryAddress()));
        return dto;
    }

    private OrderItemDto orderItemToOrderItemDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProduct(productMapper.productToProductDto(item.getId().getProduct()));
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
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
