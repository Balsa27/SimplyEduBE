package com.simplyedu.AuthServer.entities.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInRequest {
    String email;
    String password;
}
