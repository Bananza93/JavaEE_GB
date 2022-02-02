package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "from User u left join fetch u.roles left join fetch u.personalData left join fetch u.deliveryAddress where u.username = :name")
    Optional<User> findUserByUsername(String name);
}
