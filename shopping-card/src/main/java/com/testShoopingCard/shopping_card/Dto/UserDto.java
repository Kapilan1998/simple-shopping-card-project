package com.testShoopingCard.shopping_card.Dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
