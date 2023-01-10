package com.simplyedu.AuthServer.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplyedu.AuthServer.config.exception.ExceptionConfig;
import com.simplyedu.AuthServer.entities.response.JwtResponse;
import com.simplyedu.AuthServer.entities.request.LogInRequest;
import com.simplyedu.AuthServer.entities.request.RegisterRequest;
import com.simplyedu.AuthServer.exceptions.EmailNotFoundException;
import com.simplyedu.AuthServer.repository.UserRepository;
import com.simplyedu.AuthServer.service.AuthService;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class, AuthService.class, ExceptionConfig.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean(name = "authService")
    private AuthService authService;
    
    @MockBean
    private UserRepository userRepository;


    @Test
    void login_pass() throws Exception {
        //Arrange
        LogInRequest logInRequest = new LogInRequest();

        //Act
        logInRequest.setEmail("jane.doe@example.org");
        logInRequest.setPassword("iloveyou");

        String content = (new ObjectMapper()).writeValueAsString(logInRequest);

        when(authService.login((LogInRequest) any()))
                .thenReturn(new JwtResponse("ABC123", 123L, "janedoe", "jane.doe@example.org", new HashSet<>()));
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"token\":\"ABC123\",\"id\":123,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"roles\":[]}"));
    }

//    @Test
//    void login_fails() throws Exception {
//        //Arrange
//        LogInRequest logInRequest = new LogInRequest();
//
//        //Act
//        logInRequest.setEmail("jane.doe@example.org");
//        logInRequest.setPassword("iloveyou");
//
//        String content = (new ObjectMapper()).writeValueAsString(logInRequest);
//
//        when(authService.login((LogInRequest) any()))
//                .thenThrow(new EmailNotFoundException("email not found"));
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//
//        //Assert
//        MockMvcBuilders.standaloneSetup(authController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }

    
    @Test
    void testRegister_pass() throws Exception {
        //Arrange
        RegisterRequest registerRequest = new RegisterRequest("balsa", "balsa@gamil", "123456");
        String content = (new ObjectMapper()).writeValueAsString(registerRequest);

        //Act
        when(authService.register(any()))
                .thenReturn(new JwtResponse("ABC123", 123L, "janedoe", "jane.doe@example.org", new HashSet<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"token\":\"ABC123\",\"id\":123,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"roles\":[]}"));
    }
}

