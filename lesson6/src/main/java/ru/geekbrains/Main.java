package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.configuration.AppConfig;
import ru.geekbrains.service.InformationService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        InformationService infoServ = context.getBean("informationService", InformationService.class);

        System.out.println(infoServ.getProductById(3L));
        System.out.println(infoServ.getClientById(1L));

        infoServ.printAllClientsByProduct(3L);
        infoServ.printAllProductsByClient(1L);
    }
}
