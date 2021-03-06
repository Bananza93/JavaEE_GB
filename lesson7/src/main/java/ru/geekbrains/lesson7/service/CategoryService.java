package ru.geekbrains.lesson7.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.lesson7.aspect.TrackExecutionTime;
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

    @TrackExecutionTime
    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория " + name + " не найдена"));
    }

    @TrackExecutionTime
    public List<String> getAllNames() {
        return categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList());
    }
}
