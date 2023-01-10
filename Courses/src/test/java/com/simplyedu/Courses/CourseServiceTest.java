package com.simplyedu.Courses;

import com.simplyedu.Courses.config.Jwt.AuthUser;
import com.simplyedu.Courses.entities.Category;
import com.simplyedu.Courses.entities.Chapter;
import com.simplyedu.Courses.entities.Course;
import com.simplyedu.Courses.exceptions.CategoryNotFoundException;
import com.simplyedu.Courses.exceptions.CourseNotFoundException;
import com.simplyedu.Courses.exceptions.DuplicateCourseException;
import com.simplyedu.Courses.repositories.CategoryRepository;
import com.simplyedu.Courses.repositories.ChapterRepository;
import com.simplyedu.Courses.repositories.CoursePaginatedRepository;
import com.simplyedu.Courses.services.Impl.CoursePaginatedServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class CourseServiceTest {

	private CategoryRepository categoryRepository;
	private CoursePaginatedRepository coursePaginatedRepository;
	private CategoryRepository chapterRepository;
	private Authentication authentication;
	private SecurityContext contextHolder;
	private AuthUser authUser;
	private AuthenticationManager authenticationManager;
	private CoursePaginatedServiceImpl coursePaginatedService;
	private EntityManager entityManager;

	Course courseDbo = Course.builder()
			.title("balsa")
			.id(1L)
			.categories(List.of(Category.builder().name("balsa").id(3L).build()))
			.chapters(List.of(Chapter.builder().title("balsa").id(3L).build()))
			.longDescription("balsa")
			.shortDescription("balsa")
			.numberOfStudents(1)
			.creationDate(LocalDate.now())
			.lastUpdateDate(LocalDate.now())
			.imageUrl("balsa")
			.lengthInMinutes(1)
			.language("balsa")
			.price(1)
			.rating(1)
			.build();

	@BeforeEach
	void setUp() {
		categoryRepository = mock(CategoryRepository.class);
		categoryRepository = mock(CategoryRepository.class);
		coursePaginatedRepository = mock(CoursePaginatedRepository.class);
		entityManager = mock(EntityManager.class);
		coursePaginatedService = new CoursePaginatedServiceImpl(coursePaginatedRepository, entityManager, categoryRepository);

		//course = mock(CourseDbo.class);

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

		Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		Mockito.when(contextHolder.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getPrincipal()).thenReturn(authUser);
		SecurityContextHolder.setContext(contextHolder);
	}

	@Test
	void getAll_Pass1(){
		CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
		
	}

	@Test
	void getCourseById_success(){
		//Act
		Mockito.when(coursePaginatedRepository.findById(Mockito.any())).thenReturn(Optional.of(courseDbo));

		//Assert
		assertNotNull(coursePaginatedService.getCourseById(Mockito.any()));	
	}

	@Test
	void getCourseById_throws_courseNotFound(){
		//Arrange
		Course course = courseDbo;
		course.setId(2L);
		//Act
		Mockito.when(coursePaginatedRepository.findById(2L)).thenReturn(Optional.empty());

		//Assert
		assertThrows(CourseNotFoundException.class, () -> coursePaginatedService.getCourseById(1L));
	}

	@Test
	void deleteCourseById_success(){
		//Act
		Mockito.when(coursePaginatedRepository.existsById(1L)).thenReturn(true);

		//Assert
		assertEquals("Course deleted", coursePaginatedService.deleteCourseById(1L).getMessage()); 
	}

	@Test
	void updateCourseById_throws_courseNotFound(){
		//Act
		Mockito.when(coursePaginatedRepository.getReferenceById(courseDbo.getId())).thenReturn(null);

		//Assert
		assertThrows(CourseNotFoundException.class,() -> coursePaginatedService.updateCourseById(new Course()));
	}

	@Test 
	void updateCourseById_throws_duplicateEntry(){
		//Arrange
		Course courseToBeUpdated = Course.builder().title("balsa").id(1L).build();

		//Act
		Mockito.when(coursePaginatedRepository.getReferenceById(courseDbo.getId())).thenReturn(null);


		//Assert
		assertThrows(CourseNotFoundException.class,()->coursePaginatedService.updateCourseById(courseToBeUpdated));
	}

	@Test
	void saveCourse_success(){
		//Act
		
		Mockito.when(coursePaginatedRepository.save(courseDbo)).thenReturn(courseDbo);
		Mockito.when(categoryRepository.findByName("balsa"))
				.thenReturn(Category.builder().name("balsa").id(3L).build());
		
		//Assert
		assertNotNull(coursePaginatedService.saveCourse(courseDbo));
	}
	
	@Test
	void saveCourse_throws_courseAlreadyExists(){
			
		//Act
		Mockito.when(coursePaginatedRepository.existsByTitle(courseDbo.getTitle())).thenReturn(true);
		
		//Assert
		assertThrows(DuplicateCourseException.class,()->coursePaginatedService.saveCourse(courseDbo));
	}
	
	@Test
	void saveCourse_throws_duplicateEntry(){
		//Arrange
		Course courseToBeUpdated = Course.builder().title("balsa").id(1L).build();

		//Act
		Mockito.when(coursePaginatedRepository.existsByTitle(courseDbo.getTitle())).thenReturn(true);

		//Assert
		assertThrows(DuplicateCourseException.class,() -> coursePaginatedService.saveCourse(courseToBeUpdated));
	}

	@Test
	void getAllCategoryNames_success(){
		//Arrange
		List<String> names = List.of("1", "2");
		//Act
		Mockito.when(coursePaginatedRepository.getAllCategoryNames()).thenReturn(names);

		//Assert
		assertNotNull(coursePaginatedService.getAllCategoryNames());
	}

	@Test
	void getAllCategoryNames_throws_categoryNotFound(){
		//Act
		Mockito.when(coursePaginatedRepository.getAllCategoryNames()).thenReturn(null);

		//Assert
		assertThrows(CategoryNotFoundException.class,()->coursePaginatedService.getAllCategoryNames());
	}

	@Test
	void getAllByCourseId_success(){
		//Act
		Mockito.when(coursePaginatedRepository.findAllById(List.of(1L,2L))).thenReturn(List.of(courseDbo));

		//Assert
		assertNotNull(coursePaginatedService.getAllByCourseIds(List.of(1L,2L)));
	}

	@Test
	void getAllByCourseId_throws_courseNotFound(){
		//Act
		Mockito.when(coursePaginatedRepository.findAllById(List.of(3L,4L))).thenReturn(null);

		//Assert
		assertThrows(CourseNotFoundException.class,()->coursePaginatedService.getAllByCourseIds(List.of(3L,4L)));
	}
}
