package com.testShoopingCard.shopping_card.Controller;

import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Entity.Cart;
import com.testShoopingCard.shopping_card.Entity.User;
import com.testShoopingCard.shopping_card.Service.Interfaces.CartInterface;
import com.testShoopingCard.shopping_card.Service.Interfaces.CartItemInterface;
import com.testShoopingCard.shopping_card.Service.Interfaces.UserInterface;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/v1/cart-item")
@Validated
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemInterface cartItemInterface;
    private final CartInterface cartInterface;
    private final UserInterface userInterface;


    @PostMapping("/addItemToCart")
    public ResponseEntity<ApiResponseDto> addItemToCart(@RequestParam Integer productId,
                                                        @RequestParam Integer quantity) {
        try {
            User user = userInterface.getAuthenticatedUser();
            Cart cart = cartInterface.initializeNewCart(user);
            cartItemInterface.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponseDto("Item added successfully to cart !!!", null));
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(), null));
        }catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponseDto(e.getMessage(),null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponseDto> removeItemFromCart(@PathVariable Integer cartId, @PathVariable Integer itemId) {
        try {
            cartItemInterface.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponseDto("Item removed successfully from cart", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponseDto> updateItemQuantity(@PathVariable Integer cartId,
                                                             @PathVariable Integer productId,
                                                             @RequestParam Integer quantity) {
        try {
            cartItemInterface.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponseDto("Item updated in the cart", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponseDto(e.getMessage(), null));
        }
    }
}
