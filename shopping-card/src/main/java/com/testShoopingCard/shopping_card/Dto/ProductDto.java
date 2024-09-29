package com.testShoopingCard.shopping_card.Dto;

import com.testShoopingCard.shopping_card.Entity.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Data
@Validated
public class ProductDto {
    private Integer id;
    @NotBlank(message = "Product name is required")
    @Size(min = 3,max = 25 ,message = "Product name must be from 3 to 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Product name can only contain alphabets, numbers, and spaces")
    private String name;
    private BigDecimal price;
    private int inventory;
    private String brand;
    private String description;
    private CategoryDto categoryDto;
    private List<ImageDto> imageDtoList;
}
