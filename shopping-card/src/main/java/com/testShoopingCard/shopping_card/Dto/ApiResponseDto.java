package com.testShoopingCard.shopping_card.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponseDto {
    private String message;
    private Object data;
}
