package com.testShoopingCard.shopping_card.Repository;

import com.testShoopingCard.shopping_card.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByUserId(Integer userId);
}
