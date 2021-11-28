package ru.geekbrains;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, "Apple", 10.0));
        products.add(new Product(2, "Orange", 20.0));
        products.add(new Product(3, "Banana", 30.0));
        products.add(new Product(4, "Watermelon", 40.0));
        products.add(new Product(5, "Lemon", 50.0));
        products.add(new Product(6, "Strawberry", 60.0));
        products.add(new Product(7, "Pineapple", 70.0));
        products.add(new Product(8, "Mango", 80.0));
        products.add(new Product(9, "Kiwi", 90.0));
        products.add(new Product(10, "Coconut", 100.0));

        resp.getWriter().println(getProductTable(products));
    }

    private String getProductTable(List<Product> productList) {
        StringBuilder result = new StringBuilder("<html><body><table>");
        result.append("<tr><th>Id</th><th>Title</th><th>Cost</th></tr>");

        for (Product product : productList) {
            result.append("<tr>");

            result.append("<td>").append(product.getId()).append("</td>")
                    .append("<td>").append(product.getTitle()).append("<td>")
                    .append("<td>").append(product.getCost()).append("<td>");

            result.append("</tr>");
        }

        return result.append("</table></body></html>").toString();
    }
}
