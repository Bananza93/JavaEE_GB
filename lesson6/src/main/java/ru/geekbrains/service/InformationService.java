package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ClientDAO;
import ru.geekbrains.dao.ProductDAO;
import ru.geekbrains.model.Client;
import ru.geekbrains.model.Product;

@Component("informationService")
public class InformationService {

    private ProductDAO productDAO;
    private ClientDAO clientDAO;

    public InformationService() {
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    @Autowired
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Product getProductById(Long productId) {
        return productDAO.getProductById(productId);
    }

    public Client getClientById(Long clientId) {
        return clientDAO.getClientById(clientId);
    }

    public void printAllClientsByProduct(Long productId) {
        Product product = productDAO.getProductByIdWithClients(productId);
        System.out.println(product.getTitle() + " buyers list:");
        product.getClients().forEach(System.out::println);
    }

    public void printAllProductsByClient(Long clientId) {
        Client client = clientDAO.getClientByIdWithProducts(clientId);
        System.out.println(client.getName() + "'s purchase list:");
        client.getProducts().forEach(System.out::println);
    }
}
