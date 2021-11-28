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
        return productList.get(id - 1);
    }
}
