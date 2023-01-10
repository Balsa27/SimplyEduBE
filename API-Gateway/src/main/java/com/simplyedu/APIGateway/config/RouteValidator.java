package com.simplyedu.APIGateway.config;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Request;
import java.util.List;
import java.util.function.Predicate;

import static java.util.List.*;

@Component
public class RouteValidator {

    private List<String> publicRoutes = of(
            "api/auth/login",
            "api/auth/register",
            "courses/all",
            "courses/course",
            "courses/category"); 

    public boolean isProtected(ServerHttpRequest request) {
        return publicRoutes
                .stream()
                .noneMatch(publicRoute -> request
                        .getURI()
                        .getPath()
                        .contains(publicRoute));
    }
}
