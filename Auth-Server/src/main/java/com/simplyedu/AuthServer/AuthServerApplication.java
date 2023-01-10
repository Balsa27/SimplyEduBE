package com.simplyedu.AuthServer;

import com.simplyedu.AuthServer.entities.Role;
import com.simplyedu.AuthServer.entities.RoleName;
import com.simplyedu.AuthServer.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
	
	@Autowired
	RoleRepository roleRepository;
	
	//@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			roleRepository.save(Role.builder().name(RoleName.ROLE_USER).build());
			roleRepository.save(Role.builder().name(RoleName.ROLE_ADMIN).build());
		};
	}
	
}
