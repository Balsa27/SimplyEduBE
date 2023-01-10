package com.simplyedu.AuthServer.Service.ServiceTest;

import com.simplyedu.AuthServer.config.jwt.AuthUser;
import com.simplyedu.AuthServer.config.jwt.JwtUtils;
import com.simplyedu.AuthServer.entities.request.LogInRequest;
import com.simplyedu.AuthServer.entities.request.RegisterRequest;
import com.simplyedu.AuthServer.exceptions.DuplicateEmailException;
import com.simplyedu.AuthServer.entities.*;
import com.simplyedu.AuthServer.repository.RoleRepository;
import com.simplyedu.AuthServer.repository.UserRepository;
import com.simplyedu.AuthServer.service.Impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AuthServiceImplTest {

    private static final LogInRequest REQUEST = new LogInRequest("balsa@gmail.com", "123456");
    private static final RegisterRequest REGISTER_REQUEST = 
            new RegisterRequest("balsa","balsa@gmail","12345");
    private UserRepository mockUserRepository;
    private RoleRepository mockRoleRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private AuthServiceImpl authServiceMock;
    private Authentication authentication;
    private SecurityContext contextHolder;
    private AuthUser authUser;
    
    @BeforeEach
    void setUp() throws Exception {
       
        //Arrange
        mockUserRepository = mock(UserRepository.class);
        mockRoleRepository = mock(RoleRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        authentication = mock(Authentication.class);
        contextHolder = mock(SecurityContext.class);
        authUser = mock(AuthUser.class);
        encoder = mock(PasswordEncoder.class);
        jwtUtils = mock(JwtUtils.class);
        authServiceMock = new AuthServiceImpl(
                mockRoleRepository,
                mockUserRepository,
                jwtUtils,
                authenticationManager,
                encoder);
       
        //Act
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(contextHolder.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(authUser);
        Mockito.when(jwtUtils.generateJwtToken(Mockito.any())).thenReturn("jwt");
        Mockito.when(mockRoleRepository.findByName(Mockito.any()))
                .thenReturn(Optional.of(Role.builder().name(RoleName.ROLE_USER).build()));
        Mockito.when(mockUserRepository.save(Mockito.any())).thenReturn(User.builder()
                        .email(REGISTER_REQUEST.getEmail())
                        .roles(Set.of(Role.builder().name(RoleName.ROLE_USER).build()))
                        .username(REGISTER_REQUEST.getUsername())
                        .password(REGISTER_REQUEST.getPassword())
                        .build());
        
        SecurityContextHolder.setContext(contextHolder);
    }
    
    @Test
    void login_success() {
        //Act
        Mockito.when(mockUserRepository.existsByEmail(Mockito.any())).thenReturn(true);
        
        //Assert
        assertNotNull(authServiceMock.login(REQUEST));  
        assertNotNull(authServiceMock.login(REQUEST).getToken());
    }   
    
    @Test
    void login_fail() {
        //Act
        Mockito.when(mockUserRepository.existsByEmail(Mockito.any())).thenReturn(false);
        
        //Assert
        assertThrows(DuplicateEmailException.class, () -> authServiceMock.login(REQUEST));
    }
    
    @Test
    void register_success(){
        //Assert
        assertNotNull(authServiceMock.register(REGISTER_REQUEST));
        assertNotNull(authServiceMock.register(REGISTER_REQUEST).getToken());
        assertEquals(authServiceMock.register(REGISTER_REQUEST).getEmail(), REGISTER_REQUEST.getEmail());
        assertEquals(authServiceMock.register(REGISTER_REQUEST).getUsername(), REGISTER_REQUEST.getUsername());
    }
    
    @Test
    void register_fail() {
        //Act
        Mockito.when(mockUserRepository.existsByEmail(Mockito.any())).thenReturn(true);
        
        //Assert
        assertThrows(DuplicateEmailException.class, () -> authServiceMock.register(REGISTER_REQUEST));
    }
}