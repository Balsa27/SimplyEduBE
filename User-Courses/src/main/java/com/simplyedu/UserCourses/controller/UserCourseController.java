package com.simplyedu.UserCourses.controller;

import com.simplyedu.UserCourses.entities.response.EnrollUserInCourseResponse;
import com.simplyedu.UserCourses.entities.response.FindAllCoursesEnrolledByUserIdResponse;
import com.simplyedu.UserCourses.entities.response.FindUserEnrolledCourseResponse;
import com.simplyedu.UserCourses.service.UserCourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

//course user info
@RestController()
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {
    private final UserCourseService userCourseService;

    public UserCourseController(@Qualifier("user-course-service") UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @GetMapping(value = "user-courses/enrolled/{courseId}")
    @RolesAllowed("ROLE_USER")
    ResponseEntity<FindUserEnrolledCourseResponse> findUserEnrolledCourse(@PathVariable("courseId") Long courseId){
       return ResponseEntity.ok(userCourseService.findUserEnrolledCourse(courseId));
    }

    @GetMapping(value = "user-courses/all")
    @RolesAllowed("ROLE_USER")
    ResponseEntity<FindAllCoursesEnrolledByUserIdResponse> findAllUserEnrolledCourses(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(userCourseService.findAllCoursesEnrolledByUserId(PageRequest.of(page, size)));
    }
    
    @PostMapping(value = "user-courses/enroll/{courseId}")
    ResponseEntity<EnrollUserInCourseResponse> enrollUserInCourse(@PathVariable("courseId") Long courseId){
        return ResponseEntity.ok(userCourseService.enrollUserInCourse(courseId));
    }
}
