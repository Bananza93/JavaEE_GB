import dao.ProductDAO;
import model.Product;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        ProductDAO productDAO = new ProductDAO(sessionFactory);

        System.out.println("------STEP 1------");
        System.out.println(productDAO.findById(1L));
        System.out.println("------STEP 2------");
        productDAO.findAll().forEach(System.out::println);
        System.out.println("------STEP 3------");
        productDAO.deleteById(3L);
        productDAO.findAll().forEach(System.out::println);
        System.out.println("------STEP 4------");
        Product pr = new Product("Monitor", 600);
        System.out.println(productDAO.saveOrUpdate(pr));
        System.out.println("------STEP 5------");
        pr.setPrice(700);
        System.out.println(productDAO.saveOrUpdate(pr));
        System.out.println();
        productDAO.findAll().forEach(System.out::println);

        sessionFactory.close();
    }
}
