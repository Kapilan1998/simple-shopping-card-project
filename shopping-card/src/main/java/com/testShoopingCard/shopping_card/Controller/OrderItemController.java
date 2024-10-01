package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Service.Interfaces.OrderItemInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orderItem")
@Validated
@RequiredArgsConstructor
public class OrderItemController {
//    private final OrderItemInterface orderItemInterface;
}
