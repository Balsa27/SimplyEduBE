package com.simplyedu.UserCourses.config.Exception;

import com.simplyedu.UserCourses.exceptions.CourseNotFoundException;
import com.simplyedu.UserCourses.exceptions.EnrollmentException;
import com.simplyedu.UserCourses.exceptions.ForbiddenException;
import com.simplyedu.UserCourses.exceptions.ServiceNotAvailableException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class) // This annotation is used to handle exceptions across the whole application
public class ExceptionConfig {
    @ExceptionHandler(value = {
            EnrollmentException.class
    })
    public ResponseEntity<?> businessLogicException(Exception e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(value = {
            CourseNotFoundException.class
    })
    public ResponseEntity<String> courseNotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    
    @ExceptionHandler(value = {
            ForbiddenException.class,
    })
    public ResponseEntity<String> forbiddenException(Exception e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
    
    @ExceptionHandler(value = {
            ServiceNotAvailableException.class
    })
    public ResponseEntity<String> serviceNotAvailableException(Exception e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}
