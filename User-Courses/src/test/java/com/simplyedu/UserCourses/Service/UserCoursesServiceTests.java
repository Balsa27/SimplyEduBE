package com.simplyedu.UserCourses.Service;

import com.simplyedu.UserCourses.config.Jwt.AuthUser;
import com.simplyedu.UserCourses.controller.UserCourseController;
import com.simplyedu.UserCourses.exceptions.CourseNotFoundException;
import com.simplyedu.UserCourses.exceptions.EnrollmentException;
import com.simplyedu.UserCourses.http.model.response.Course;
import com.simplyedu.UserCourses.http.service.CourseService;
import com.simplyedu.UserCourses.http.service.PurchaseService;
import com.simplyedu.UserCourses.http.service.SubscriptionService;
import com.simplyedu.UserCourses.entities.Enrollment;
import com.simplyedu.UserCourses.repository.UserCourseRepository;
import com.simplyedu.UserCourses.service.impl.UserCourseServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserCoursesServiceTests {
	
	private UserCourseServiceImpl userCoursesService;
	private UserCourseRepository userCourseRepository;
	private CourseService courseService;
	private PurchaseService purchaseService;
	private SubscriptionService subscriptionService;
	
	private Authentication authentication;
	private SecurityContext contextHolder;
	private AuthenticationManager authenticationManager;
	

	@BeforeEach
	void setUp() {
		//Arrange
		userCourseRepository = mock(UserCourseRepository.class);
		courseService = mock(CourseService.class);
		purchaseService = mock(PurchaseService.class);
		subscriptionService = mock(SubscriptionService.class);
		userCoursesService = new UserCourseServiceImpl(userCourseRepository,courseService,purchaseService,subscriptionService);
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
	public void findEnrolledUserCourse_success() throws Exception{
		//Act
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(Course.builder().id(1L).build());
		Mockito.when(userCourseRepository.findByUserIdAndCourseId(Mockito.any(),Mockito.any()))
				.thenReturn(Optional.of(Enrollment.builder().build()));
		
		//Assert
		assertNotNull(userCoursesService.findUserEnrolledCourse(1L));
	}
	
	@Test
	void findEnrolledUserCourse_throws_courseNotFound(){
		//Arrange
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(null);
		
		//Assert
		assertThrows(CourseNotFoundException.class,()->userCoursesService.findUserEnrolledCourse(1L));
	}
	
	@Test
	void findEnrolledUserCourse_throws_enrollmentException(){
		//Act
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(Course.builder().id(1L).build());
		Mockito.when(userCourseRepository.findByUserIdAndCourseId(Mockito.any(),Mockito.any()))
				.thenReturn(Optional.empty());
		
		
		//Assert
		assertThrows(EnrollmentException.class,()->userCoursesService.findUserEnrolledCourse(1L));
	}
	
	@Test
	void findAllCoursesEnrolledByUserId_success(){
		//Arrange
		Course response = Course.builder().id(1L).build();
		Pageable pageable = PageRequest.of(1,2);
		Page<Enrollment> dbos = new PageImpl<>(List.of(Enrollment.builder().build()));
		
		//Act
		Mockito.when(userCourseRepository.findAllByUserId(Mockito.any(), Mockito.any()))
				.thenReturn(dbos);
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(response);
		
		//Assert
		assertNotNull(userCoursesService.findAllCoursesEnrolledByUserId(pageable));
	}
	
	@Test
	void findAllCoursesEnrolledByUserId_throw_enrollmentException(){
		//Arrange
		Pageable pageable = PageRequest.of(1,2);
		
		//Act
		Mockito.when(userCourseRepository.findAllByUserId(Mockito.any(), Mockito.any()))
				.thenReturn(null);
	
		//Assert
		assertThrows(EnrollmentException.class,()->userCoursesService.findAllCoursesEnrolledByUserId(pageable));
	}
	
	@Test
	void enrollUserInCourse_success(){
		//Act
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(Course.builder().id(1L).build());
		Mockito.when(userCourseRepository.findByUserIdAndCourseId(1L,1L))
				.thenReturn(Optional.empty());
		//mockStatic(EnrollmentMapper.class);
		Mockito.when(purchaseService.isPurchased(1L)).thenReturn(true);
		
		//Assert
		assertNotNull(userCoursesService.enrollUserInCourse(1L));
	}
	
	@Test
	void enrollUserInCourse_throws_courseNotFound(){
		//Act
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(null);
		
		//Assert
		assertThrows(CourseNotFoundException.class,()->userCoursesService.enrollUserInCourse(1L));
	}
	
	@Test
	void enrollUserInCourse_throws_enrollmentException(){
		//Act
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(Course.builder().id(1L).build());
		Mockito.when(userCourseRepository.findByUserIdAndCourseId(Mockito.any(),Mockito.any()))
				.thenReturn(Optional.of(Enrollment.builder().build()));
		
		//Assert
		assertThrows(EnrollmentException.class,()->userCoursesService.enrollUserInCourse(1L));
	}
	
	@Test
	void enrollUserInCourse_throws_enrollmentException_2(){
		//Act
		Mockito.when(courseService.getCourseById(Mockito.any())).thenReturn(Course.builder().id(1L).build());
		Mockito.when(userCourseRepository.findByUserIdAndCourseId(Mockito.any(),Mockito.any()))
				.thenReturn(Optional.empty());
		Mockito.when(purchaseService.isPurchased(1L)).thenReturn(false);
		
		//Assert
		assertThrows(EnrollmentException.class,()->userCoursesService.enrollUserInCourse(1L));
	}
}
