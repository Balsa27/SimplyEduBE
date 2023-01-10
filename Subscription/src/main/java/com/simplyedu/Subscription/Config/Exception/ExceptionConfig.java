package com.simplyedu.Subscription.Config.Exception;

import com.simplyedu.Subscription.exception.SubscribeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class) // This annotation is used to handle exceptions across the whole application
public class ExceptionConfig {
    @ExceptionHandler(value = {
            SubscribeException.class
    })
    public ResponseEntity<?> businessLogicException(Exception e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }
}
