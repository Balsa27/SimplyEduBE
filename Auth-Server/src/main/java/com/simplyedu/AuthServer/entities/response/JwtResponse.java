package com.simplyedu.AuthServer.entities.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class JwtResponse {
    String token;
    Long id;    
    String username;
    String email;
    Set<String> roles;
}
