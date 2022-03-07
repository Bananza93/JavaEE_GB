package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson7.model.RegistrationToken;

import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    Optional<RegistrationToken> findRegistrationTokenByToken(String token);

    void annulCurrentUserRegistrationToken(Long userId);
}
