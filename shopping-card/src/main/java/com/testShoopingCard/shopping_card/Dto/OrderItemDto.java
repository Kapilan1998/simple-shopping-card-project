package com.testShoopingCard.shopping_card.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Integer productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;
}
