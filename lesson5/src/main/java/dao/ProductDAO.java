package dao;

import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAO {
    SessionFactory sessionFactory;

    public ProductDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Product findById(Long id) {
        Product product;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            product = session.get(Product.class, id);
            session.getTransaction().commit();
        }
        return product;
    }

    public List<Product> findAll() {
        List<Product> productList;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            productList = session.createQuery("from Product p", Product.class).getResultList();
            session.getTransaction().commit();
        }
        return productList;
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("delete from Product p where p.id = :id")
                        .setParameter("id", id)
                        .executeUpdate();
                transaction.commit();
            } catch (Throwable t) {
                transaction.rollback();
                throw t;
            }
        }
    }

    public Product saveOrUpdate(Product product) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.saveOrUpdate(product);
                transaction.commit();
            } catch (Throwable t) {
                transaction.rollback();
                throw t;
            }
        }
        return product;
    }
}
