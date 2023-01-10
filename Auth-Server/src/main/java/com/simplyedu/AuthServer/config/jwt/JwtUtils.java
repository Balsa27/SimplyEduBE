package com.simplyedu.AuthServer.config.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    private final String jwtSecret = "simplyedu";
    private final int jwtExpirationMs = 36000000;


    public String generateJwtToken(Authentication authentication) {
        //secret key
        //expiration time
        AuthUser authenticatedUser = (AuthUser) authentication.getPrincipal(); //principal is the user information

        return Jwts.builder().setSubject(authenticatedUser
                        .getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date()
                        .getTime() + jwtExpirationMs))
                .addClaims(Map.of(
                        "id", authenticatedUser.getId(),
                        "email", authenticatedUser.getEmail(),
                        "roles", authenticatedUser.getAuthorities()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact(); //.build()
    }
}