package com.testShoopingCard.shopping_card.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Integer cartId;
    private BigDecimal totalAmount ;
    private Set<CartItemDto> items;
}
