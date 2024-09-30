package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Entity.Cart;

import java.math.BigDecimal;

public interface CartInterface {
    public Cart getCart(Integer id);
    void clearCart(Integer id);
    BigDecimal getTotalPrice(Integer id);

    Integer initializeNewCart();

    Cart getCartByUserId(Integer userId);
}
