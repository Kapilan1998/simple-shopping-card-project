package com.testShoopingCard.shopping_card.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LoginRequestDto {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password can't be empty")
    private String password;

}
