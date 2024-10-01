package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Dto.OrderDto;
import com.testShoopingCard.shopping_card.Entity.Order;
import com.testShoopingCard.shopping_card.Service.Interfaces.OrderInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/order")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final OrderInterface orderInterface;

    @PostMapping
    public ResponseEntity<ApiResponseDto> createOrder(@RequestParam Integer userId){
        try {
            Order order = orderInterface.placeOrder(userId);
            OrderDto orderDto = orderInterface.convertToDto(order);
            return ResponseEntity.ok(new ApiResponseDto("Item order success !",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("Error while creating order, ",e.getMessage()));
        }
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<ApiResponseDto> getOrderById(@PathVariable Integer orderId){
        try {
            OrderDto orderDto = orderInterface.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponseDto("Order details ",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("No order found , ",e.getMessage()));
        }
    }

    @GetMapping("/getUserOrders/{orderId}")
    public ResponseEntity<ApiResponseDto> getUserOrders(@PathVariable Integer orderId){
        try {
            List<OrderDto> orderDtoList = orderInterface.getUserOrders(orderId);
            return ResponseEntity.ok(new ApiResponseDto("Orders placed by this user ",orderDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto("No order found , ",e.getMessage()));
        }
    }
}
