package com.testShoopingCard.shopping_card.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {
    private Integer id;
    private Integer userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}
