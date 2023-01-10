package com.simplyedu.AuthServer.service;

import com.simplyedu.AuthServer.entities.response.JwtResponse;
import com.simplyedu.AuthServer.entities.request.LogInRequest;
import com.simplyedu.AuthServer.entities.request.RegisterRequest;

public interface AuthService {
    JwtResponse login(LogInRequest request);
    JwtResponse register(RegisterRequest request);
    //public Message send(String message);
}
