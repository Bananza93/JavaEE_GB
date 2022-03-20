package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> getCartById(String id);
}
