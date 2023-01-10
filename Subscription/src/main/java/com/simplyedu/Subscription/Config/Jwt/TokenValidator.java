package com.simplyedu.Subscription.Config.Jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TokenValidator {
    private final String jwtSecret = "simplyedu";
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidator.class);

    public boolean validateJwtToken(String token) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
       }catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
    }
        return false;
    }
    
    public AuthUser getUserDetails(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        List<Map> roles = (List<Map>) claims.get("roles");
        
        List<SimpleGrantedAuthority> authorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority((String) role.get("authority")))
                .toList();
        
        return new AuthUser(
                claims.get("id", Long.class),
                claims.get("email", String.class),
                null, //no pw
                claims.getSubject(),
                authorities);
    }
}
