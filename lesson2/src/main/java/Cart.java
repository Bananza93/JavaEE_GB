import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("cart")
@Scope("prototype")
public class Cart {
    private Map<Product, Integer> productMap;
    private ProductRepository prodRep;

    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

    @Autowired
    public void setProductMap(Map<Product, Integer> productMap) {
        this.productMap = productMap;
    }

    public ProductRepository getProdRep() {
        return prodRep;
    }

    @Autowired
    public void setProdRep(ProductRepository prodRep) {
        this.prodRep = prodRep;
    }

    public void addToCart(int id, int count) {
        Product prod = prodRep.getProduct(id);
        productMap.put(prod, productMap.getOrDefault(prod, 0) + count);
        System.out.println("Product " + prod.getTitle() + " (Qnt: " + count + ") added.");
    }

    public void removeFromCart(int id, int count) {
        Product prod = prodRep.getProduct(id);
        int currQnt = productMap.getOrDefault(prod, 0);
        if (currQnt == 0) {
            System.out.println("There is no such product in cart");
            return;
        }
        int afterRemoveQnt = currQnt - count;
        if (afterRemoveQnt > 0) {
            productMap.replace(prod, afterRemoveQnt);
            System.out.println("Product " + prod.getTitle() + " (Qnt: " + count + ") removed.");
        } else {
            productMap.remove(prod);
            System.out.println("Product " + prod.getTitle() + " (Qnt: " + currQnt + ") removed.");
        }
    }
}
