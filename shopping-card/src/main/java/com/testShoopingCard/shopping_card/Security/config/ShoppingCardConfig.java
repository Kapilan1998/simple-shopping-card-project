package com.testShoopingCard.shopping_card.Security.config;

import com.testShoopingCard.shopping_card.Security.Jwt.AuthTokenFilter;
import com.testShoopingCard.shopping_card.Security.Jwt.JwtAuthEntryPoint;
import com.testShoopingCard.shopping_card.Security.Jwt.JwtUtils;
import com.testShoopingCard.shopping_card.Security.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ShoppingCardConfig {

    private final ShopUserDetailsService shopUserDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private static final List<String> SECURED_URLS = List.of("/api/v1/cart/**", "/api/v1/cart-item/**");
    private final JwtUtils jwtUtils;

    // used for mapping between DTOs and entity objects
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // PasswordEncoder implementation (BCryptPasswordEncoder) to hash passwords securely
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // filter that processes the incoming requests to check for JWT tokens and validate them.
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(jwtUtils,shopUserDetailsService);
    }

    // responsible for authenticating the users.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // integrates the UserDetailsService (for user data) and the PasswordEncoder to perform authentication.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(shopUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)                  // CSRF protection is disabled because JWT is being used, and JWT is inherently secure against CSRF attacks since it doesn't rely on cookies.
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))      // JwtAuthEntryPoint is used to handle cases where authentication fails, returning a proper error response (like HTTP 401 Unauthorized).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // SessionCreationPolicy.STATELESS ensures that the application does not use sessions. This is typical in stateless authentication using JWT.
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()  // list of SECURED_URLS need authentication
                        .anyRequest().permitAll());                 // other requests do not need any authentication
        httpSecurity.authenticationProvider(daoAuthenticationProvider());       // DaoAuthenticationProvider is registered to handle authentication.
        httpSecurity.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);        // AuthTokenFilter is added to process the JWT validation before the UsernamePasswordAuthenticationFilter
        return httpSecurity.build();
    }
}
