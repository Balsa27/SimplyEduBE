package com.simplyedu.UserCourses.service;

import com.simplyedu.UserCourses.entities.response.*;
import org.springframework.data.domain.Pageable;

public interface UserCourseService {
    FindUserEnrolledCourseResponse findUserEnrolledCourse(Long courseId);
    FindAllCoursesEnrolledByUserIdResponse findAllCoursesEnrolledByUserId(Pageable page);
    EnrollUserInCourseResponse enrollUserInCourse(Long courseId);
}
