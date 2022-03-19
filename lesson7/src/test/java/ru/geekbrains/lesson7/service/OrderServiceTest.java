package ru.geekbrains.lesson7.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.lesson7.dto.UserCheckoutDto;
import ru.geekbrains.lesson7.model.Cart;
import ru.geekbrains.lesson7.model.Order;
import ru.geekbrains.lesson7.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class OrderServiceTest {

    private Cart cart;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        given(cartService.getCartForCurrentUser()).willReturn(cart);
    }

    @Test
    void makeOrderEmptyCartTest() {
        UserCheckoutDto ucd = new UserCheckoutDto();
        assertThrows(IllegalStateException.class, () -> orderService.makeOrder(ucd, null));
    }

    @Test
    void makeOrderTest() {
        cart.addProduct(productRepository.findById(1L).get(), 2);
        UserCheckoutDto ucd = new UserCheckoutDto();
        ucd.setPhoneNumber("+77777777777");

        Order order = orderService.makeOrder(ucd, null);

        assertNotNull(order.getId());
        assertSame(1, order.getOrderItems().size());
        assertEquals("notPaid", order.getOrderStatus().get(0).getCode());
    }
}