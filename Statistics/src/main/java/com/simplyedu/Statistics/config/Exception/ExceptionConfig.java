package com.simplyedu.Statistics.config.Exception;

import com.simplyedu.Statistics.exception.ForbiddenException;
import com.simplyedu.Statistics.exception.ServiceNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class) // This annotation is used to handle exceptions across the whole application
public class ExceptionConfig {
    @ExceptionHandler(value = {
            ForbiddenException.class,
    })
    public ResponseEntity<?> forbiddenException(Exception e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(value = {
            ServiceNotAvailableException.class
    })
    public ResponseEntity<?> serviceNotAvailableException(Exception e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}
