package shop;

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

    public ProductRepository getProdRep() {
        return prodRep;
    }

    @Autowired
    public void setProductMap(Map<Product, Integer> productMap) {
        this.productMap = productMap;
    }

    @Autowired
    public void setProdRep(ProductRepository prodRep) {
        this.prodRep = prodRep;
    }

    public void addToCart(int id, int count) {
        if (id <= 0 || count <= 0) {
            System.out.println("Parameters id and count must be > 0");
            return;
        }
        Product prod;
        if ((prod = prodRep.getProduct(id)) == null) return;
        productMap.put(prod, productMap.getOrDefault(prod, 0) + count);
        System.out.println("Product " + prod.getTitle() + " (Qnt: " + count + ") added.");
    }

    public void removeFromCart(int id, int count) {
        if (id <= 0 || count <= 0) {
            System.out.println("Parameters id and count must be > 0");
            return;
        }
        Product prod;
        if ((prod = prodRep.getProduct(id)) == null) return;
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

    public void printCart() {
        if (productMap.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            double totalPrice = 0.0;
            for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
                Product currProd = entry.getKey();
                int qnt = entry.getValue();
                double totalPriceForProduct = currProd.getCost() * qnt;
                totalPrice += totalPriceForProduct;
                String s = String.format("%4d\t%-20s\t%4d\t%.2f$", currProd.getId(), currProd.getTitle(), qnt, totalPriceForProduct);
                System.out.println(s);
            }
            System.out.printf("%36s\t%.2f$\n", "Total price:", totalPrice);
        }
    }
}
