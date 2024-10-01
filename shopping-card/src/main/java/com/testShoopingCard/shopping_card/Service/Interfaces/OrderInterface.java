package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Dto.OrderDto;
import com.testShoopingCard.shopping_card.Entity.Order;

import java.util.List;

public interface OrderInterface {
    Order placeOrder(Integer userId);
    OrderDto getOrder(Integer orderId);

    List<OrderDto> getUserOrders(Integer userId);

    OrderDto convertToDto(Order order);
}
