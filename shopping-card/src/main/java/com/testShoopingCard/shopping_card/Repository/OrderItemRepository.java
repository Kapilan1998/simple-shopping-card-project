package com.testShoopingCard.shopping_card.Repository;

import com.testShoopingCard.shopping_card.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
