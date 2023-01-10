package com.simplyedu.UserCourses.http.service;

import com.simplyedu.UserCourses.config.FeignConfig.CustomErrorDecoder;
import com.simplyedu.UserCourses.config.FeignConfig.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "SUBSCRIPTION", configuration = {
        FeignConfig.class,
        CustomErrorDecoder.class
})
public interface SubscriptionService {
    @GetMapping("/subscription/is-subscribed")
    Boolean isSubscribed();
}
