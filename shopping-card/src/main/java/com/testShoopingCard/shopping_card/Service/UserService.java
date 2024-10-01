package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Dto.SaveUserDto;
import com.testShoopingCard.shopping_card.Dto.UpdateUserDto;
import com.testShoopingCard.shopping_card.Dto.UserDto;
import com.testShoopingCard.shopping_card.Entity.User;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.UserRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ServiceException("User id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public User createUser(SaveUserDto saveUserDto) {
        return Optional.of(saveUserDto)
                .filter(user -> !userRepository.existsByEmail(saveUserDto.getEmail()))
                .map(req -> {
                    User userData = new User();
                    userData.setFirstName(saveUserDto.getFirstName());
                    userData.setLastName(saveUserDto.getLastName());
                    userData.setEmail(saveUserDto.getEmail());
                    userData.setPassword(saveUserDto.getPassword());
                    return userRepository.save(userData);
                }).orElseThrow(() -> new ServiceException("Email already exists", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
    }

    @Override
    public User updateUser(UpdateUserDto updateUserDto) {
        if (updateUserDto.getUserId() == null) {
            throw new ServiceException("User id is required", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        return userRepository.findById(updateUserDto.getUserId()).map(existingUser -> {
            existingUser.setFirstName(updateUserDto.getFirstName());
            existingUser.setLastName(updateUserDto.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ServiceException("User not found", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST));
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ServiceException("User not found", Applicationconstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        });
    }

    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
