package ru.geekbrains.lesson7.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.lesson7.model.Category;
import ru.geekbrains.lesson7.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryByTitle(String title) {
        return categoryRepository.findCategoryByTitle(title).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория " + title + " не найдена"));
    }

    public List<String> getAllTitles() {
        return categoryRepository.findAll().stream().map(Category::getTitle).collect(Collectors.toList());
    }
}
