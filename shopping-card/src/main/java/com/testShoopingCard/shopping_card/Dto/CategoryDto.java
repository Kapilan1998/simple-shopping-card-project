package com.testShoopingCard.shopping_card.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "Category name is required")
    private String name;
}
