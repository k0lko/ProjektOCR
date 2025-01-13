package com.projektocr.Controller;

import com.projektocr.Model.Category;
import com.projektocr.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CategoryController.API_CATEGORIES)
@RequiredArgsConstructor
public class CategoryController {

    public static final String API_CATEGORIES = "/api/categories"; // Extract Constant

    private final CategoryService categoryService;

    // Pobierz wszystkie kategorie
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Pobierz pojedynczą kategorię po ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found")); // Inline Optional.orElseGet
        return buildResponseEntity(HttpStatus.OK, category); // Refactor with Extract Method
    }

    // Utwórz nową kategorię
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category createdCategory = categoryService.saveCategory(category);
        return buildResponseEntity(HttpStatus.CREATED, createdCategory); // Reuse Extracted Method
    }

    // Extracted Method for ResponseEntity Building
    private ResponseEntity<Category> buildResponseEntity(HttpStatus status, Category category) {
        return ResponseEntity.status(status).body(category);
    }
}