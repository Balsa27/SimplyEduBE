package com.simplyedu.AuthServer.config.exception;

import com.simplyedu.AuthServer.exceptions.DuplicateEmailException;
import com.simplyedu.AuthServer.exceptions.EmailNotFoundException;
import com.simplyedu.AuthServer.exceptions.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class) // This annotation is used to handle exceptions across the whole application
public class ExceptionConfig {
    @ExceptionHandler(value = {
            EmailNotFoundException.class,
            DuplicateEmailException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<?> badRequestException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = {
            AuthenticationException.class
    })
    public ResponseEntity<?> lognException(Exception e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
