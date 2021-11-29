package shop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainApp {

    private static Cart currentCart;

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        currentCart = context.getBean("cart", Cart.class);

        System.out.println("Welcome to PC components shop!");
        System.out.println("Type <help> to see the list of commands.");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String[] nextInput = br.readLine().strip().split("\\s+");
                processCommand(nextInput);
            }
        }
    }

    private static void processCommand(String[] command) {
        switch (command[0]) {
            case "add" -> {
                if (command.length < 3) return;
                try {
                    int id = Integer.parseInt(command[1]);
                    int count = Integer.parseInt(command[2]);
                    currentCart.addToCart(id, count);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect parameters for command \"add\"");
                }
            }
            case "remove" -> {
                if (command.length < 3) return;
                try {
                    int id = Integer.parseInt(command[1]);
                    int count = Integer.parseInt(command[2]);
                    currentCart.removeFromCart(id, count);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect parameters for command \"remove\"");
                }
            }
            case "products" -> printProducts();
            case "cart" -> printCart();
            case "help" -> printHelp();
            case "exit" -> System.exit(0);
            default -> System.out.println("No such command \"" + command[0] + "\"");
        }
    }

    public static void printCart() {
        currentCart.printCart();
    }

    public static void printProducts() {
        currentCart.getProdRep().printProductList();
    }

    public static void printHelp() {
        InputStream is = MainApp.class.getClassLoader().getResourceAsStream("help.txt");
        if (is != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String nextLine;
                while ((nextLine = br.readLine()) != null) {
                    System.out.println(nextLine);
                }
            } catch (IOException e) {
                System.out.println("Exception in \"printHelp\": " + e.getCause() + " | " + e.getMessage());
            }
        }
    }
}
