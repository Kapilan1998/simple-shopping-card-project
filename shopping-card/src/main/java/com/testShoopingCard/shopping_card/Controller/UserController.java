package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Dto.SaveUserDto;
import com.testShoopingCard.shopping_card.Dto.UpdateUserDto;
import com.testShoopingCard.shopping_card.Dto.UserDto;
import com.testShoopingCard.shopping_card.Entity.User;
import com.testShoopingCard.shopping_card.Service.Interfaces.UserInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserInterface userInterface;


    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<ApiResponseDto> getUserById(@PathVariable Integer userId) {
        try {
            User user = userInterface.getUserById(userId);
            UserDto userDto = userInterface.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponseDto("User details ", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("User not found", e.getMessage()));
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<ApiResponseDto> createUser(@Valid @RequestBody SaveUserDto saveUserDto) {
        try {
            User user = userInterface.createUser(saveUserDto);
            UserDto userDto = userInterface.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponseDto("User created", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("Error while creating user ", e.getMessage()));
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponseDto> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto) {
        try {
            User user = userInterface.updateUser(updateUserDto);
            UserDto userDto = userInterface.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponseDto("User updated", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("Error while updating user ", e.getMessage()));
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Integer userId){
        try {
            userInterface.deleteUser(userId);
            return  ResponseEntity.ok(new ApiResponseDto("User deleted",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("Error while deleting user ",e.getMessage()));
        }
    }

}
