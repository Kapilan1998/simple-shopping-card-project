package com.testShoopingCard.shopping_card.Repository;

import com.testShoopingCard.shopping_card.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUserId(Integer userId);
}
