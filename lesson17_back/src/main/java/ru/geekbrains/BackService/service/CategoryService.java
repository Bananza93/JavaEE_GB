package ru.geekbrains.BackService.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.BackService.exception.CategoryNotFoundException;
import ru.geekbrains.BackService.model.Category;
import ru.geekbrains.BackService.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }
}
