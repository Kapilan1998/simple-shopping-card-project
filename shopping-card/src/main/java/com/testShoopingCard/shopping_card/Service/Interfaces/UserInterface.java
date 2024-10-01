package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Dto.SaveUserDto;
import com.testShoopingCard.shopping_card.Dto.UpdateUserDto;
import com.testShoopingCard.shopping_card.Dto.UserDto;
import com.testShoopingCard.shopping_card.Entity.User;

public interface UserInterface {
    User getUserById(Integer userId);
    User createUser(SaveUserDto saveUserDto);
    User updateUser(UpdateUserDto updateUserDto);
    void deleteUser(Integer userId);

    UserDto convertUserToDto(User user);
}
