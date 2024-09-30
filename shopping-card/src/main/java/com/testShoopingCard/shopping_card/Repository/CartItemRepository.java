package com.testShoopingCard.shopping_card.Repository;

import com.testShoopingCard.shopping_card.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    void deleteAllByCartId(Integer id);
}
