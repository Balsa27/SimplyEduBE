package com.simplyedu.Statistics.config.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    TokenValidator validator;
    
    @Autowired
    RouteValidator routeValidator;
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
       
        if(!routeValidator.isProtected(request)){
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = request.getHeader("Authorization");
        
        if(token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token found in request headers");
            return;
        }
        
        String jwt = token.split(" ")[1];
       
        if(!validator.validateJwtToken(jwt)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is not valid");
            return;
        }
        
        AuthUser user = validator.getUserDetails(jwt);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        filterChain.doFilter(request, response);
    }
}
