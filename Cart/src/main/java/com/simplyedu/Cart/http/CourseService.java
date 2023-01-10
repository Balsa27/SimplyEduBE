package com.simplyedu.Cart.http;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("COURSES")
public interface CourseService {
    @GetMapping("/courses/course/{id}")
    Course getCourseById(@PathVariable("id") Long id);
}