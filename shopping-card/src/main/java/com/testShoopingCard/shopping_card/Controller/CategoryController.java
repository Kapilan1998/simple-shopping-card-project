package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Dto.CategoryDto;
import com.testShoopingCard.shopping_card.Dto.ResponseDto;
import com.testShoopingCard.shopping_card.Entity.Category;
import com.testShoopingCard.shopping_card.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/category")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseDto saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    @GetMapping("/getById")
    public CategoryDto getCategoryById(@RequestParam("id") Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/getAll")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/updateCategory")
    public ResponseDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<ApiResponseDto> getCategoryByName(@PathVariable String name) {
        try {
            Optional<Category> category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponseDto("Data is found", category));
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponseDto("Category deleted", null));
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(), null));
        }
    }

}
