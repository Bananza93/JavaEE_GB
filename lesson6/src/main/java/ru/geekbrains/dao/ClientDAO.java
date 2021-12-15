package ru.geekbrains.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.model.Client;

@Component("clientDAO")
public class ClientDAO {

    SessionFactory sessionFactory;

    public ClientDAO() {
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Client getClientById(Long id) {
        return getClientById0(id, false);
    }

    public Client getClientByIdWithProducts(Long id) {
        return getClientById0(id, true);
    }

    private Client getClientById0(Long id, boolean withProducts) {
        Client client;
        StringBuilder query = new StringBuilder("select c from Client c");
        if (withProducts) query.append(" join fetch c.products");
        query.append(" where c.id = :id");

        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            client = session.createQuery(query.toString(), Client.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.getTransaction().commit();
        }

        return client;
    }
}
