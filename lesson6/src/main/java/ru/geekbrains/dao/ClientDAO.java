package ru.geekbrains.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.model.Client;
import ru.geekbrains.model.Product;

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
        return getClientById(id, false);
    }

    public Client getClientByIdWithProducts(Long id) {
        return getClientById(id, true);
    }

    private Client getClientById(Long id, boolean withProducts) {
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

    public void addNewClient(Client client) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    public void updateClient(Client client) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            session.update(client);
            session.getTransaction().commit();
        }
    }

    public void deleteClient(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            session.delete(session.load(Client.class, id));
            session.getTransaction().commit();
        }
    }
}
