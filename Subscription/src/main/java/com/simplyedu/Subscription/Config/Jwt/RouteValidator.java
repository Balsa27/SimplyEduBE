package com.simplyedu.Subscription.Config.Jwt;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.List.of;

@Component
public class RouteValidator {

    private  List<String> publicRoutes = of("h2-console", "swagger-ui/",   "/v3/api-docs"); //add all public methods

    public boolean isProtected(HttpServletRequest request) {
        return publicRoutes
                .stream()
                .noneMatch(publicRoute -> request
                        .getRequestURI()
                        .contains(publicRoute));
    }
}
