import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {

    private static ProductRepository prodRep;
    private static Cart currentCart;

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        prodRep = context.getBean("prodRep", ProductRepository.class);
        currentCart = context.getBean("cart", Cart.class);

        System.out.println("Welcome to PC components shop!");
        System.out.println("Type <help> to see the list of commands.");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String nextInput = br.readLine();
                switch (nextInput) {
                    case "help" -> printHelp();
                    case "exit" -> System.exit(0);
                    case "products" -> printProducts();
                }
            }
        }
    }

    public static void printCart() {

    }

    public static void printProducts() {
        for (Product prod : prodRep.getAllProducts()) {
            System.out.println(prod);
        }
    }

    public static void printHelp() {
        System.out.println("List of available commands:");
        System.out.println("help - \tprint help");
        System.out.println("exit - \tclose program");
        System.out.println("products - \tlist of available products");
        System.out.println("add id qnt- \tadd product with that ID and quantity to cart");
        System.out.println("remove id qnt- \tremove product with that ID and quantity from cart");
    }
}
