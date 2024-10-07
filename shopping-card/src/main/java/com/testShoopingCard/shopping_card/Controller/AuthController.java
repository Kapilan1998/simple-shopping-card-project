package com.testShoopingCard.shopping_card.Controller;


import com.testShoopingCard.shopping_card.Dto.ApiResponseDto;
import com.testShoopingCard.shopping_card.Dto.JwtResponseDto;
import com.testShoopingCard.shopping_card.Dto.LoginRequestDto;
import com.testShoopingCard.shopping_card.Security.Jwt.JwtUtils;
import com.testShoopingCard.shopping_card.Security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(),requestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponseDto jwtResponseDto = new JwtResponseDto(userDetails.getId(), jwtToken);
            return ResponseEntity.ok(new ApiResponseDto("Login success",jwtResponseDto));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(e.getMessage(),null));
        }

    }

}
