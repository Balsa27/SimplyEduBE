package com.simplyedu.AuthServer.service.Impl;

import com.simplyedu.AuthServer.config.jwt.JwtUtils;
import com.simplyedu.AuthServer.config.jwt.AuthUser;
import com.simplyedu.AuthServer.entities.request.LogInRequest;
import com.simplyedu.AuthServer.entities.request.RegisterRequest;
import com.simplyedu.AuthServer.entities.response.JwtResponse;
import com.simplyedu.AuthServer.exceptions.DuplicateEmailException;
import com.simplyedu.AuthServer.exceptions.RoleNotFoundException;
import com.simplyedu.AuthServer.entities.*;
import com.simplyedu.AuthServer.repository.RoleRepository;
import com.simplyedu.AuthServer.repository.UserRepository;
import com.simplyedu.AuthServer.service.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils; 
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    
    public AuthServiceImpl(@Qualifier("roleRepository")RoleRepository roleRepository,
                           @Qualifier("userRepository") UserRepository userRepository,
                           JwtUtils jwtUtils,
                           AuthenticationManager authenticationManager, 
                           PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }
    
    public JwtResponse login(LogInRequest request){
        //un and pw from the request are loaded in spring security context
        //spring security will authenticate the user and generate a jwt token after successful authentication
        //we will return the jwt token to the client
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Invalid credentials");
        }
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser user = (AuthUser) authentication.getPrincipal();
        
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        return new JwtResponse(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
    }
    
    private Set<Role> getRoles(Set<String> roles){
        return roles.stream()
                .map(role -> roleRepository.findByName(RoleName.valueOf(role))
                        .orElseThrow(() -> new RoleNotFoundException("Role not found")))
                .collect(Collectors.toSet());
    }

    public JwtResponse register(RegisterRequest request){

        //save user to db
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already taken");
        }

        User savedUser = userRepository.save(User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RoleNotFoundException("Role not found"))))
                .build());

        AuthUser user = AuthUser.builder()
                .id(savedUser.getId())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(Set.of(new SimpleGrantedAuthority(RoleName.ROLE_USER.name())))
                .build();
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        return new JwtResponse(jwtToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
    }

//    @Override
//    public Message send(String message) {
//        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(user == null)
//            throw new MustBeAutenticatedException("Must be authenticated to send messages in the community learning");
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        //return new Message(user.getUsername(), message, time);
//        return null;
//    }
}
