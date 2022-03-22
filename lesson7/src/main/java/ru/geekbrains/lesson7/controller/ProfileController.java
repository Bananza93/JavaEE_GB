package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.mapper.OrderMapper;
import ru.geekbrains.lesson7.service.ProfileService;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final OrderMapper orderMapper;

    public ProfileController(ProfileService profileService, OrderMapper orderMapper) {
        this.profileService = profileService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/orders")
    public String getUserOrdersPage(@ModelAttribute OrderDto order, BindingResult result, Model model, Principal principal) {
        model.addAttribute("orderDto", new OrderDto());
        model.addAttribute("orders", profileService.getUserOrders(principal)
                .stream()
                .map(orderMapper::orderToOrderDto)
                .toList());
        return "user_orders";
    }

    @GetMapping("/orders/{id}")
    public String showUserOrderDetailsPage(Model model, @PathVariable Long id, Principal principal) {
        model.addAttribute("order", orderMapper.orderToOrderDetailsDto(profileService.getUserOrderDetails(principal, id)));
        return "user_order_details";
    }
}
