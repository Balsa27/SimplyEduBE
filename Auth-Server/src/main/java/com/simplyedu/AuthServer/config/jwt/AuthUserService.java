package com.simplyedu.AuthServer.config.jwt;

import com.simplyedu.AuthServer.exceptions.EmailNotFoundException;
import com.simplyedu.AuthServer.entities.User;
import com.simplyedu.AuthServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

    //todo AuthUserService manages how the auth user is loaded into the system 
    private final UserRepository userRepository;

    public AuthUserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EmailNotFoundException("Email not found"));
        return AuthUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .roles(user.getRoles()
                        .stream()
                        .map(role ->  new SimpleGrantedAuthority(role
                                .getName()
                                .name()))
                        .toList())
                .build();
    }
}
