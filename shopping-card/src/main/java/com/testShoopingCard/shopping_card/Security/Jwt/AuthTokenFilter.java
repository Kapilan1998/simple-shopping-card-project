package com.testShoopingCard.shopping_card.Security.Jwt;


import com.testShoopingCard.shopping_card.Security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// this class is custom filter that intercepts each HTTP request to validate the JWT provided in the Authorization header
// Use @RequiredArgsConstructor to automatically generate constructor with required dependencies
@Slf4j
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final ShopUserDetailsService shopUserDetailsService;

    /**
     *
     * How it Works in the Request Lifecycle:
     * Intercepting Requests: This filter intercepts every incoming HTTP request.
     * Extracting the JWT: It checks the Authorization header for a JWT token. If found, the token is extracted.
     * Validating the JWT: The token is validated using the JwtUtils class. If the token is invalid or expired, a 401 Unauthorized response is returned.
     * Loading User: If the token is valid, it extracts the username and uses the ShopUserDetailsService to load the user's details from the database.
     * Setting Authentication: The filter then creates a UsernamePasswordAuthenticationToken and sets it into the SecurityContextHolder. This makes the user "authenticated" for this request.
     * Passing the Request: After authentication, the request proceeds down the filter chain for further processing.
     *
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);         // extracts the JWT from the Authorization header of the request.
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {      // If the token is valid, the filter continues.
                String userName = jwtUtils.getUserNameFromJwtToken(jwt);        // get user name from token
                log.info("generated user name "+ userName);
                UserDetails userDetails = shopUserDetailsService.loadUserByUsername(userName);   // checks user name from database
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);       // application recognizes the user as authenticated for the remainder of the request.
            }
        } catch (JwtException e) {                  // filter has 2 levels of error handling
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        // regarding issue with the token
            response.getWriter().write(e.getMessage() + " : Invalid or expired token");
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);       // if any other exceptions are caught
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {      // prefixed by "Bearer "
            return headerAuth.substring(7);             //  // Extracts the token after "Bearer "
        }
        return null;
    }
}
