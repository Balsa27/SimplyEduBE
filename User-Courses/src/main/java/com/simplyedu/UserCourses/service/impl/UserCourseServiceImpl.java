package com.simplyedu.UserCourses.service.impl;
import com.simplyedu.UserCourses.config.Jwt.AuthUser;
import com.simplyedu.UserCourses.http.model.response.Course;
import com.simplyedu.UserCourses.entities.response.EnrollUserInCourseResponse;
import com.simplyedu.UserCourses.entities.response.FindAllCoursesEnrolledByUserIdResponse;
import com.simplyedu.UserCourses.entities.response.FindUserEnrolledCourseResponse;
import com.simplyedu.UserCourses.exceptions.CourseNotFoundException;
import com.simplyedu.UserCourses.exceptions.EnrollmentException;
import com.simplyedu.UserCourses.http.service.CourseService;
import com.simplyedu.UserCourses.http.service.PurchaseService;
import com.simplyedu.UserCourses.http.service.SubscriptionService;
import com.simplyedu.UserCourses.entities.CourseItem;
import com.simplyedu.UserCourses.entities.Enrollment;
import com.simplyedu.UserCourses.repository.UserCourseRepository;
import com.simplyedu.UserCourses.service.UserCourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("user-course-service")
public class UserCourseServiceImpl implements UserCourseService {
    private final UserCourseRepository userCourseRepository;
        private final CourseService courseService;
        private final PurchaseService purchaseService;
        private final SubscriptionService subscriptionService;

    public UserCourseServiceImpl(
            @Qualifier("user-courses-jpa-repository") UserCourseRepository userCourseRepository,
            CourseService courseService, 
            PurchaseService purchaseService,
            SubscriptionService subscriptionService) {
        this.userCourseRepository = userCourseRepository;
        this.courseService = courseService;
        this.purchaseService = purchaseService;
        this.subscriptionService = subscriptionService;
    }
    
    //one course by id
    @Override
    public FindUserEnrolledCourseResponse findUserEnrolledCourse(Long courseId){
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
        Course course = courseService.getCourseById(courseId); 

        if(course == null)
            throw new CourseNotFoundException("Course doesn`t exist");

        Optional<Enrollment> userEnrolledCourse = userCourseRepository
                .findByUserIdAndCourseId(user.getId(), courseId);
        
        
        if(userEnrolledCourse.isEmpty() )
            throw new EnrollmentException("User is not enrolled in this course");
        
        return new FindUserEnrolledCourseResponse(course, userEnrolledCourse.get().getRating());
                
    }

    //all user courses
    @Override
    public FindAllCoursesEnrolledByUserIdResponse findAllCoursesEnrolledByUserId(Pageable page){

        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Enrollment> enrolledCourses = userCourseRepository.findAllByUserId(user.getId(), page);
        
        if(enrolledCourses == null) 
            throw new EnrollmentException("User is not enrolled in any course");

        List<CourseItem> courseItems = enrolledCourses.get().map(enrolledCourse -> {
            Course course = courseService.getCourseById(enrolledCourse.getCourseId());
            return new CourseItem(course, enrolledCourse.getRating());
        }).toList();

        Page<CourseItem> userCourseItemPage = new PageImpl<>(
                courseItems,
                enrolledCourses.getPageable(),
                enrolledCourses.getTotalElements());

        return new FindAllCoursesEnrolledByUserIdResponse(userCourseItemPage);
    }

    @Override
    public EnrollUserInCourseResponse enrollUserInCourse(Long courseId){
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Course course = courseService.getCourseById(courseId); //exists by id
        
        if(course == null)
            throw new CourseNotFoundException("Course doesn`t exist");

        Optional<Enrollment> userEnrolledCourse = userCourseRepository
                .findByUserIdAndCourseId(user.getId(), courseId);
        
        if(userEnrolledCourse.isPresent()) 
            throw new EnrollmentException("User is already enrolled in this course");

        if(!purchaseService.isPurchased(courseId) && !subscriptionService.isSubscribed())
            throw new EnrollmentException("User is not subscribed or has not purchased this course");
        
        userCourseRepository.save(Enrollment.builder()
                .userId(user.getId())
                .courseId(courseId)
                .rating(0)
                .build());
        
        return new EnrollUserInCourseResponse("User enrolled in course successfully");
    }
}
