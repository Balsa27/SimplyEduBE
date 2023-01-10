package com.simplyedu.UserCourses.http.service;

import com.simplyedu.UserCourses.config.FeignConfig.CustomErrorDecoder;
import com.simplyedu.UserCourses.config.FeignConfig.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PURCHASES", configuration = {
        FeignConfig.class,
        CustomErrorDecoder.class

})
public interface PurchaseService {
    @GetMapping("purchases/is-purchased/{courseId}")
    Boolean isPurchased(@PathVariable("courseId") Long courseId);
}
