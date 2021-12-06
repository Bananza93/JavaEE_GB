package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("prodRep")
public class ProductRepository {

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    @Autowired
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Product getProduct(int id) {
        if (id > productList.size()) {
            System.out.println("Product with id = " + id + " not found.");
            return null;
        }
        return productList.get(id - 1);
    }

    public void printProductList() {
        for (Product prod : productList) {
            System.out.println(prod);
        }
    }
}
