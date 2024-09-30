package com.testShoopingCard.shopping_card.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class SaveUserDto {
    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "Maximum length of first name is 15 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabetic letters are allowed to first name.")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Maximum length of last name is 15 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabetic letters are allowed to last name.")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$"
            , message = "Invalid email format. Please provide a valid email address.")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,12}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;
}
