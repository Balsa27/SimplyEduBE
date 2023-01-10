package com.simplyedu.Courses.config.Jwt;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.List.of;

@Component
public class RouteValidator {

    private  List<String> publicRoutes = of("courses/get-all", "courses/category", "courses/course/",  "h2-console" ,  "swagger-ui/",   "/v3/api-docs"); //add all public methods

    public boolean isProtected(HttpServletRequest request) {
        return publicRoutes
                .stream()
                .noneMatch(publicRoute -> request
                        .getRequestURI()
                        .contains(publicRoute));
    }
}