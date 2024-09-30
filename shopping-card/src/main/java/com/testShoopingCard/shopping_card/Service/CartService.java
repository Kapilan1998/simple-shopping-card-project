package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Entity.Cart;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.CartItemRepository;
import com.testShoopingCard.shopping_card.Repository.CartRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.CartInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CartService implements CartInterface {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicInteger cardIdGenerator = new AtomicInteger(0);

    @Override
    public Cart getCart(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cart id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Integer id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Integer id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Integer initializeNewCart(){
        Cart newCart = new Cart();
        Integer newCardId = cardIdGenerator.incrementAndGet();
        newCart.setId(newCardId);
        return cartRepository.save(newCart).getId();
    }

}
