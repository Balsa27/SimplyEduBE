package com.simplyedu.UserCourses.http.service;

import com.simplyedu.UserCourses.config.FeignConfig.CustomErrorDecoder;
import com.simplyedu.UserCourses.config.FeignConfig.FeignConfig;
import com.simplyedu.UserCourses.http.model.response.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "COURSES", configuration = {
        FeignConfig.class,
        CustomErrorDecoder.class
})
public interface CourseService {
    @GetMapping(value = "courses/course/{id}")
    Course getCourseById(@PathVariable(value = "id") Long id);
}
