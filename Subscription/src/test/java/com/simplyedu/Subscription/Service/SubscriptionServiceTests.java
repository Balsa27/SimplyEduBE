package com.simplyedu.Subscription.Service;

import com.simplyedu.Subscription.Config.Jwt.AuthUser;
import com.simplyedu.Subscription.entities.Subscription;
import com.simplyedu.Subscription.exception.SubscribeException;
import com.simplyedu.Subscription.entities.Request.SubscriptionRequest;
import com.simplyedu.Subscription.entities.Response.SubscriptionStatisticsResponse;
import com.simplyedu.Subscription.repository.SubscriptionRepository;
import com.simplyedu.Subscription.service.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SubscriptionServiceTests {

	private SubscriptionServiceImpl subscriptionService;
	private SubscriptionRepository subscriptionRepository;

	private Authentication authentication;
	private SecurityContext contextHolder;
	private AuthUser authUser;
	private AuthenticationManager authenticationManager;
	
	@BeforeEach
	void setUp() {
		//Arrange
		subscriptionRepository = mock(SubscriptionRepository.class);
		subscriptionService = new SubscriptionServiceImpl(subscriptionRepository);
		authenticationManager = mock(AuthenticationManager.class);
		authentication = mock(Authentication.class);
		contextHolder = mock(SecurityContext.class);
		AuthUser authUser = AuthUser.builder()
				.id(1L)
				.email("balsa.stevovoic@gmail.com")
				.username("balsa")
				.roles(Set.of(new SimpleGrantedAuthority("ROLE_USER")))
				.password("123456")
				.build();
		
		//Act
		Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		Mockito.when(contextHolder.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getPrincipal()).thenReturn(authUser);
		SecurityContextHolder.setContext(contextHolder);
	}

	@Test
	void subscribe_success(){
		//Arrange
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
		
		//Act
		Mockito.when(subscriptionRepository.findByUserId(Mockito.anyLong()))
				.thenReturn(Optional.of(Subscription.builder().expirationDate(LocalDate.now()).build()));
		
		//Assert
		assertEquals(subscriptionService.subscribe().getMessage(), "You have successfully subscribed");
	}
	
	@Test
	void subscribe_throws_subscriptionException(){
		//Arrange
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
		
		//Act
		Mockito.when(subscriptionRepository.findByUserId(Mockito.anyLong()))
				.thenReturn(Optional.of(Subscription.builder().expirationDate(LocalDate.now().plusDays(1)).build()));
		
		//Assert
		assertThrows(SubscribeException.class, () -> subscriptionService.subscribe());
	}

	@Test
	void isSubscribed_true(){
		//Act
		Mockito.when(subscriptionRepository.existsByUserId(Mockito.anyLong()))
				.thenReturn(true);
		
		//Assert
		assertEquals(subscriptionService.isSubscribed(), true);
	}
	
	@Test
	void isSubscribed_false(){
		//Arrange
		Mockito.when(subscriptionRepository.existsByUserId(Mockito.anyLong()))
				.thenReturn(false);
		
		//Assert
		assertEquals(subscriptionService.isSubscribed(), false);
	}
	
	@Test
	void getSubscriptionStatistics_success(){
		//Arrange
		List<Subscription> subscriptions = new ArrayList<>();
		subscriptions.add(Subscription.builder()
				.userId(1L)
				.startDate(LocalDate.now())
				.expirationDate(LocalDate.now().plusMonths(1))
				.isActive(true)
				.build());
		//Act
		Mockito.when(subscriptionRepository.findAll()).thenReturn(subscriptions);
		
		//Assert
		assertNotNull(subscriptionService.getSubscriptionStatistics());
	}
}
