package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.RegistrationToken;
import ru.geekbrains.lesson7.model.User;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    @Query("select rt.user from RegistrationToken rt where rt.token = :token and rt.expiredAt < :currentTime")
    Optional<User> findUserByToken(String token, Instant currentTime);
}
