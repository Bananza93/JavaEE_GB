package ru.geekbrains.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.model.Product;

@Component("productDAO")
public class ProductDAO {

    SessionFactory sessionFactory;

    public ProductDAO() {
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Product getProductById(Long id) {
        return getProductById(id, false);
    }

    public Product getProductByIdWithClients(Long id) {
        return getProductById(id, true);
    }

    private Product getProductById(Long id, boolean withClients) {
        Product product;
        StringBuilder query = new StringBuilder("select p from Product p");
        if (withClients) query.append(" join fetch p.clients");
        query.append(" where p.id = :id");

        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            product = session.createQuery(query.toString(), Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.getTransaction().commit();
        }

        return product;
    }

    public void addNewProduct(Product product) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    public void updateProduct(Product product) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            session.update(product);
            session.getTransaction().commit();
        }
    }

    public void deleteProduct(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.getTransaction().begin();
            session.delete(session.load(Product.class, id));
            session.getTransaction().commit();
        }
    }
}
