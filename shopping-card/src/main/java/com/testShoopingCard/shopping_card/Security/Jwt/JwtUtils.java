package com.testShoopingCard.shopping_card.Security.Jwt;

import com.testShoopingCard.shopping_card.Security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMills}")
    private int expirationTime;

    public String generateTokenForUser(Authentication authentication) {
        // get the logged user
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

        //list of roles that user have
        // Retrieves the user’s roles from GrantedAuthority, which are encoded into the JWT as a claim.
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        //details needs to be included in the token going to generate
        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())       // subject of token
                .claim("id ", userPrincipal.getId())      // Custom data adding ,using claims appending user id inside token
                .claim("roles ", roles)                    // Custom data adding , using claims appending roles inside token
                .setIssuedAt(new Date())                    // token created time(current time LocalTime)
                .setExpiration(new Date((new Date()).getTime() + expirationTime))   // expiration time
                .signWith(key(), SignatureAlgorithm.HS256)      // secret key , algorithm used
                .compact();     // generate token
    }

    // generates a key used to sign the token
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));               // secret key needs to encode
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key()).build()      //Verifies the token with the secret key obtained from the key() method and builds the parser.
                .parseSignedClaims(token)         //Parses the signed claims (the payload) from the JWT token.
                .getPayload().getSubject();     //Retrieves the subject (username) from the payload and returns
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SecurityException |
                 IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
