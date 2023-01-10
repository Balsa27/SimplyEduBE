package com.simplyedu.UserCourses.controller;

import static org.mockito.Mockito.any;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplyedu.UserCourses.entities.CourseItem;
import com.simplyedu.UserCourses.entities.response.FindAllCoursesEnrolledByUserIdResponse;
import com.simplyedu.UserCourses.entities.response.FindUserEnrolledCourseResponse;
import com.simplyedu.UserCourses.http.model.response.Course;
import com.simplyedu.UserCourses.http.service.CourseService;
import com.simplyedu.UserCourses.service.UserCourseService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserCourseController.class})
@ExtendWith(SpringExtension.class)
class UserCourseControllerTest {
    @Autowired
    private UserCourseController userCourseController;

    @MockBean(name = "user-course-service")
    private UserCourseService userCourseService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext contextHolder;

    @MockBean
    private AuthenticationManager authenticationManager;


    /**
     * Method under test: {@link UserCourseController#findUserEnrolledCourse(Long)}
     */
    @Test
    void testFindUserEnrolledCourse() throws Exception {
        Long courseId = 123L;
        String content = (new ObjectMapper()).writeValueAsString(courseId);
        
        Course course = new Course(
                123L,
                "course",
                "course",
                "course",
                "course",
                3.0,
                3,
                3.0, 
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                3, 
                "English"
        );
        
        FindUserEnrolledCourseResponse response = new FindUserEnrolledCourseResponse(course,3);
        Mockito.when(userCourseService.findUserEnrolledCourse(any())).thenReturn(response);
        Mockito.when(courseService.getCourseById(123L)).thenReturn(course);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user-courses/enrolled/" + courseId)
                .param("courseId", String.valueOf(courseId))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userCourseController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    /**
     * Method under test: {@link UserCourseController#findAllUserEnrolledCourses(int, int)}
     */
    @Test
    void testFindAllUserEnrolledCourses() throws Exception {
        int page = 1;
        int size = 1;

        String content = (new ObjectMapper()).writeValueAsString(PageRequest.of(page, size));
        CourseItem courseItem = new CourseItem(Course.builder().id(123L).build(),3 );
        Page<CourseItem> courseItemPage = new PageImpl<>(List.of(courseItem));
        FindAllCoursesEnrolledByUserIdResponse userCoursesResponse = new FindAllCoursesEnrolledByUserIdResponse(courseItemPage);
        

        Mockito.when(userCourseService.findAllCoursesEnrolledByUserId(PageRequest.of(page, size))).thenReturn(userCoursesResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user-courses/all")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userCourseController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}

