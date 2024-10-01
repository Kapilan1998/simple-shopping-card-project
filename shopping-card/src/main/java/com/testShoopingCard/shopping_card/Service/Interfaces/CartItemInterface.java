package com.testShoopingCard.shopping_card.Service.Interfaces;

import com.testShoopingCard.shopping_card.Entity.CartItem;

public interface CartItemInterface {
    void addItemToCart(Integer cartId,Integer productId,int quantity);
    void removeItemFromCart(Integer cartId,Integer productId);
    void updateItemQuantity(Integer cartId,Integer productId,int quantity);

    CartItem getCartItem(Integer cartId, Integer productId);
}
