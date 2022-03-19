package ru.geekbrains.lesson7.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
}
