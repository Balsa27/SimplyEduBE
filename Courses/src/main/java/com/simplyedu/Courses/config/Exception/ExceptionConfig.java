package com.simplyedu.Courses.config.Exception;

import com.simplyedu.Courses.exceptions.CategoryNotFoundException;
import com.simplyedu.Courses.exceptions.CourseNotFoundException;
import com.simplyedu.Courses.exceptions.DuplicateCourseException;
import com.simplyedu.Courses.exceptions.InvalidSortingStrategyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class) // This annotation is used to handle exceptions across the whole application
public class ExceptionConfig {
    @ExceptionHandler(value = {
            DuplicateCourseException.class,
            InvalidSortingStrategyException.class,
            CategoryNotFoundException.class
    })
    public ResponseEntity<?> businessLogicException(Exception e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(value = {
            CourseNotFoundException.class
    })
    public ResponseEntity<?> courseNotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }
}
