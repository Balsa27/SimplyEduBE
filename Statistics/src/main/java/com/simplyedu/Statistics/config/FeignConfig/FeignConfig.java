package com.simplyedu.Statistics.config.FeignConfig;

import feign.RequestInterceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


public class FeignConfig {

    @Bean
    public RequestInterceptor bearerAuthRequestInterceptor() {
        return requestTemplate -> {
            if (RequestContextHolder.getRequestAttributes() != null
                    && RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                String authorization = request.getHeader("Authorization");
                if (StringUtils.isNotBlank(authorization)) {
                   requestTemplate.header("Authorization", new String[]{authorization});
                }
            }
        };
    }
}
