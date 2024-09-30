package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Entity.Cart;
import com.testShoopingCard.shopping_card.Entity.CartItem;
import com.testShoopingCard.shopping_card.Entity.Product;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.CartItemRepository;
import com.testShoopingCard.shopping_card.Repository.CartRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.CartInterface;
import com.testShoopingCard.shopping_card.Service.Interfaces.CartItemInterface;
import com.testShoopingCard.shopping_card.Service.Interfaces.ProductInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemInterface {
    private final CartItemRepository cartItemRepository;
    private final ProductInterface productInterface;
    private final CartInterface cartInterface;
    private final CartRepository cartRepository;

    /**
     * @param cartId
     * @param productId
     * @param quantity  1. get cart
     *                  2.get product
     *                  3.check if the product already in the cart
     *                  4.if yes. then increase the quantity with given quantity
     *                  5.if no, initiate a new CartItem entry.
     */
    @Override
    public void addItemToCart(Integer cartId, Integer productId, int quantity) {
        Cart cart = cartInterface.getCart(cartId);
        Product product = productInterface.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(myItem -> myItem.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Integer cartId, Integer productId) {
        Cart cart = cartInterface.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId,productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Integer cartId, Integer productId, int quantity) {
        Cart cart = cartInterface.getCart(cartId);
        cart.getItems().stream()
                .filter(myItem -> myItem.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems().stream()
                        .map(CartItem::getTotalPrice)
                                .reduce(BigDecimal.ZERO,BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Integer cartId, Integer productId){
        Cart cart = cartInterface.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(myItem -> myItem.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ServiceException("Sorry Item not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
    }
}
