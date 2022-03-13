package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.AppUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "from AppUser u left join fetch u.roles left join fetch u.personalData left join fetch u.deliveryAddress where u.email = :email")
    Optional<AppUser> findUserByEmail(String email);

    @Query("FROM AppUser u JOIN FETCH u.roles")
    List<AppUser> findAllFetchRole();
}
