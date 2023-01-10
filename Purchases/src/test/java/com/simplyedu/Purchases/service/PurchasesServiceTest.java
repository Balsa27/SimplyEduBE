package com.simplyedu.Purchases.service;

import com.simplyedu.Purchases.config.Jwt.AuthUser;
import com.simplyedu.Purchases.entities.response.CoursesByIdResponse;
import com.simplyedu.Purchases.entities.response.PurchaseDetailedResponse;
import com.simplyedu.Purchases.exception.MalformedRequestException;
import com.simplyedu.Purchases.http.CourseService;
import com.simplyedu.Purchases.entities.Purchase;
import com.simplyedu.Purchases.entities.request.PurchaseRequest;
import com.simplyedu.Purchases.http.Course;
import com.simplyedu.Purchases.repository.PurchaseRepository;
import com.simplyedu.Purchases.service.impl.PurchaseServiceImpl;
import com.simplyedu.Purchases.util.PurchaseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class PurchasesServiceTest {

	private PurchaseServiceImpl purchasesService;
	private PurchaseRepository purchaseRepository;
	PurchaseMapper purchaseMapper;
	private CourseService courseService;
	private Authentication authentication;
	private SecurityContext contextHolder;
	private AuthenticationManager authenticationManager;
	
	@BeforeEach
	void setUp() {
		//Arrange
		courseService = mock(CourseService.class);
		purchaseRepository = mock(PurchaseRepository.class);
		purchaseMapper = mock(PurchaseMapper.class);
		purchasesService = new PurchaseServiceImpl(purchaseRepository,courseService);
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
	void purchaseCourses_success(){
		//Arrange
		Set<Long> courseIds = new HashSet<>();
		PurchaseRequest request = PurchaseRequest.builder()
				.userId(1L)
				.courseIds(courseIds)
				.build();
		
		//Act
		courseIds.add(2L);
		courseIds.add(1L);
		Mockito.when(purchaseRepository.existsByUserIdAndCourseId(1L,1L)).thenReturn(true);
		
		//Assert
		assertNotNull(purchasesService.purchaseCourses(request));
	}
	
	@Test
	void purchaseCourses_throws_malformedRequest(){
		//Arrange
		PurchaseRequest request = PurchaseRequest.builder()
				.userId(1L)
				.build();
		
		//Assert
		assertThrows(MalformedRequestException.class, () -> purchasesService.purchaseCourses(request));
	}
	
//	@Test
//	void getPurchaseStatistics_success(){
//		//Act
//		Mockito.when(purchaseRepository.count()).thenReturn(1L);
//
//		//Assert
//		assertNotNull(purchasesService.getPurchaseStatistics());
//	}
	
	@Test
	void isPurchased_success(){
		//Act
		Mockito.when(purchaseRepository.existsByUserIdAndCourseId(2L,2L)).thenReturn(true);
		
		//Assert
		assertNotNull(purchasesService.isPurchased(1L));
		assertFalse(purchasesService.isPurchased(1L));
	}


	@Test
	void getPurchasedCourses_success(){
		//Arrange
		List<Purchase> purchases = List.of(Purchase.builder().build()); 
		List<Course> courses = new ArrayList<>();
		courses.add(Course.builder().id(1l).build());
		courses.add(Course.builder().id(2l).build());
		CoursesByIdResponse response = new CoursesByIdResponse();
		response.setCourses(courses);
		
		//Act
		Mockito.when(purchaseRepository.getAllByUserId(any())).thenReturn(purchases);
		Mockito.when(courseService.getAllByCourseIds(any())).thenReturn(response);
		Mockito.when(purchaseRepository.getAllByUserId(1L)).thenReturn(purchases);
		
		//Assert
		assertNotNull(purchasesService.getPurchasedCourses());
	}

	@Test
	void getPurchasedCourses_throws_MalformedRequest(){
		//Arrange
		List<Purchase> purchases = List.of(Purchase.builder().build());
		List<Course> courses = new ArrayList<>();
		CoursesByIdResponse response = new CoursesByIdResponse();
		response.setCourses(courses);

		//Act
		Mockito.when(purchaseRepository.getAllByUserId(any())).thenReturn(null);

		//Assert
		assertThrows(MalformedRequestException.class, () -> purchasesService.getPurchasedCourses());
	}
	
	@Test
	void getAllPurchasesForStatistics(){
		Mockito.when(purchaseMapper.mapPurchaseToPurchaseDetailedResponse(any())).thenReturn(PurchaseDetailedResponse.builder().build());
		assertNotNull(purchasesService.getAllPurchasesForStatistics());
	}
}
