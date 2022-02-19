package ru.geekbrains.BackService.exception;

import org.springframework.dao.DataAccessException;

public class ProductAlreadyExistsException extends DataAccessException {

    public ProductAlreadyExistsException(String productName) {
        super("Product " + productName + " is already exists.");
    }

    public ProductAlreadyExistsException(String productName, Throwable cause) {
        super("Product " + productName + " is already exists.", cause);
    }
}
