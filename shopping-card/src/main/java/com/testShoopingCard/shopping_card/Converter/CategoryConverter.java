package com.testShoopingCard.shopping_card.Converter;

import com.testShoopingCard.shopping_card.Dto.CategoryDto;
import com.testShoopingCard.shopping_card.Entity.Category;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public Category convertToEntity(@Valid CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
