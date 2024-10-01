package com.testShoopingCard.shopping_card.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Integer itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
