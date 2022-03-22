package ru.geekbrains.lesson7.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.lesson7.mapper.CategoryMapper;
import ru.geekbrains.lesson7.model.Category;
import ru.geekbrains.lesson7.repository.CategoryRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/{id}")
    public String getProductsByCategory(@PathVariable Long id, Model model, @PageableDefault(size = 8) Pageable pageable) {
        Category category = categoryRepository.findCategoryByIdFetchProducts(id).orElse(new Category());
        model.addAttribute("category", categoryMapper.categoryToCategoryDto(category, pageable));
        return "product_list_by_category";
    }
}
