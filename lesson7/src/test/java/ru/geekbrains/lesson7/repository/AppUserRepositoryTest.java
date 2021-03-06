package ru.geekbrains.lesson7.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.geekbrains.lesson7.model.AppUser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByNameTest() {
        AppUser user = userRepository.findUserByEmail("admin@shop.ru").orElse(null);
        assertNotNull(user);
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN")));
    }
}