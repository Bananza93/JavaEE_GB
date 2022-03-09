package ru.geekbrains.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.lesson7.dto.OrderDto;
import ru.geekbrains.lesson7.dto.UserCheckoutDto;
import ru.geekbrains.lesson7.mapper.OrderMapper;
import ru.geekbrains.lesson7.mapper.UserMapper;
import ru.geekbrains.lesson7.model.User;
import ru.geekbrains.lesson7.service.CartService;
import ru.geekbrains.lesson7.service.OrderService;
import ru.geekbrains.lesson7.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    public OrderController(OrderService orderService, UserService userService, CartService cartService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getCheckoutForm(Model model, Principal principal) {
        model.addAttribute("orderPrice", cartService.getCartDto().getSumPrice());
        model.addAttribute("user", UserMapper.userToUserCheckoutDto(principal == null ? new User() : userService.getUserByEmail(principal.getName())));
        return "checkout";
    }

    @PostMapping
    public RedirectView proceedToCheckout(UserCheckoutDto ucd,
                                          Principal principal,
                                          @ModelAttribute OrderDto order,
                                          final RedirectAttributes attributes) {
        try {
            order = OrderMapper.orderToOrderDto(orderService.makeOrder(ucd, principal));
            attributes.addFlashAttribute("order", order);
            return new RedirectView("/order/success", true);
        } catch (IllegalStateException e) {
            attributes.addFlashAttribute("cartIsEmpty", e);
            return new RedirectView("/order", true);
        }
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "order_success";
    }
}
