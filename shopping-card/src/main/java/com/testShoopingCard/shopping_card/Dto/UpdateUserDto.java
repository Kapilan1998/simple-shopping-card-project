package com.testShoopingCard.shopping_card.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class UpdateUserDto {
    private Integer userId;
    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "Maximum length of first name is 15 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabetic letters are allowed to first name.")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Maximum length of last name is 15 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabetic letters are allowed to last name.")
    private String lastName;
}
