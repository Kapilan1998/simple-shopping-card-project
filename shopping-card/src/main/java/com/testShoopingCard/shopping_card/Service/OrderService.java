package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Entity.Order;
import com.testShoopingCard.shopping_card.Entity.OrderItem;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.OrderRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.OrderInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderInterface {
    private OrderRepository orderRepository;


    @Override
    public Order placeOrder(Integer userId) {
        return null;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ServiceException("Order id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
    }
}
