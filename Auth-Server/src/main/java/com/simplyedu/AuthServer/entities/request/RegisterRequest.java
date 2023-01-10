package com.simplyedu.AuthServer.entities.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Builder
public class RegisterRequest {
    String username;
    String email;
    String password;
}
