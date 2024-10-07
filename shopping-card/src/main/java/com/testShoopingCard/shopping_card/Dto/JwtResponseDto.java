package com.testShoopingCard.shopping_card.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private Integer id;
    private String token;
}
