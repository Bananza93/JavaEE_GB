package ru.geekbrains.BackService.exception;

import org.springframework.dao.DataAccessException;

public class CategoryNotFoundException extends DataAccessException {

    public CategoryNotFoundException(String categoryName) {
        super("Category " + categoryName + " not found.");
    }

    public CategoryNotFoundException(String categoryName, Throwable cause) {
        super("Category " + categoryName + " not found.", cause);
    }
}
