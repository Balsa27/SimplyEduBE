package com.simplyedu.APIGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    TokenValidator tokenValidator;
    
    @Autowired
    RouteValidator routeValidation;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //request the gateway to send the request to the auth server asynchrounously
        //filter is a layer of config
        //exchange is the info commin trough the filter
        //chain is a reference to the whole chain of filters
        //jwt is processed and send it trough the chain
        
        if(!routeValidation.isProtected(exchange.getRequest()))
            return chain.filter(exchange);
        
        ServerHttpRequest request = exchange.getRequest();

        if (!request.getHeaders().containsKey("Authorization")) {
            return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
        }
        
        String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0); //returns non null
       
        if(authHeader == null || !authHeader.startsWith("Bearer ")) { //checking if the header is null
            return onError(exchange, "No JWT token found in request headers", HttpStatus.UNAUTHORIZED);
        }
        
        String jwt = authHeader.split(" ")[1];  //split the token

        if(!tokenValidator.validateJwtToken(jwt)) //check if its valid
            return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
        return chain.filter(exchange); //if its valid, send it to the next filter
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
