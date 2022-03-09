package ru.geekbrains.lesson7.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class RegistrationTokenRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private RegistrationTokenRepository registrationTokenRepository;

    @Transactional
    public void annulCurrentUserRegistrationToken(Long userId) {
        String sql = "UPDATE registration_tokens SET expired_at = now() WHERE user_id = ? AND expired_at > now()";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.executeUpdate();
    }

}
