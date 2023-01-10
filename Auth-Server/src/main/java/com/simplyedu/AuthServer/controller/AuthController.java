package com.simplyedu.AuthServer.controller;

import com.simplyedu.AuthServer.entities.response.JwtResponse;
import com.simplyedu.AuthServer.entities.request.LogInRequest;
import com.simplyedu.AuthServer.entities.request.RegisterRequest;
import com.simplyedu.AuthServer.service.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(@Qualifier("authService") AuthService authService) {
        this.authService = authService;
    }
 
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LogInRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

//    @MessageMapping("/group-chat")
//    @SendTo("/topic/greetings")
//    public Message send(@Payload String message) {
//        return authService.send(message);
//    }
}
    