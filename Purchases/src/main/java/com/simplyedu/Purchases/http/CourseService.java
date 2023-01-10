package com.simplyedu.Purchases.http;

import com.simplyedu.Purchases.config.FeignConfig.FeignConfig;
import com.simplyedu.Purchases.entities.request.CourseIdsRequest;
import com.simplyedu.Purchases.entities.response.CoursesByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "COURSES", configuration = {
        FeignConfig.class //for authorization header to get passed
})
@CrossOrigin(origins = "*", maxAge = 3600)
public interface CourseService {
    
    @PostMapping("/courses/byids")
    CoursesByIdResponse getAllByCourseIds(@RequestBody CourseIdsRequest courseIds);
}