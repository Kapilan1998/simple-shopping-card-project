package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Entity.Order;

public interface OrderInterface {
    Order placeOrder(Integer userId);
    Order getOrder(Integer orderId);
}
