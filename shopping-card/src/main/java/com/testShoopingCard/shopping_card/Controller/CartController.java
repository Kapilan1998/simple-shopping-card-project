package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Entity.Cart;
import com.testShoopingCard.shopping_card.Service.Interfaces.CartInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/cart")
@Validated
@RequiredArgsConstructor
public class CartController {
    private final CartInterface cartInterface;


    @GetMapping("/myCart/{cartId}")
    public ResponseEntity<ApiResponseDto> getCart(@PathVariable Integer cartId) {
        try {
            Cart cart = cartInterface.getCart(cartId);
            return ResponseEntity.ok(new ApiResponseDto("Sucess", cart));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(), null));
        }
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<ApiResponseDto> clearCart(@PathVariable Integer cartId) {
        try {
            cartInterface.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponseDto("Cart cleared successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(),null));
        }
    }

    @GetMapping("/totalAmount/{cartId}")
    public ResponseEntity<ApiResponseDto> getTotalAmount (@PathVariable Integer cartId){
        try {
            BigDecimal totalAmount = cartInterface.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponseDto("Total price is ",totalAmount));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(),null));
        }
    }
}
