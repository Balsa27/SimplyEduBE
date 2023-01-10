package com.simplyedu.Courses.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplyedu.Courses.entities.response.CourseUpdateResponse;
import com.simplyedu.Courses.entities.request.CourseAddRequest;
import com.simplyedu.Courses.entities.request.CourseIdsRequest;
import com.simplyedu.Courses.entities.response.*;
import com.simplyedu.Courses.services.CoursePaginatedService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CourseController.class})
@ExtendWith(SpringExtension.class)
class CourseControllerTest {
    @Autowired
    private CourseController courseController;

    @MockBean(name = "paginatedService")
    private CoursePaginatedService coursePaginatedService;

    /**
     * Method under test: {@link CourseController#getCourseById(Long)}
     */
    @Test
    void testGetCourseById() throws Exception {
        //Act
        when(coursePaginatedService.getCourseById((Long) any())).thenReturn(new CourseByIdResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courses/course/{id}", 123L);

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }


    /**
     * Method under test: {@link CourseController#addCourse(CourseAddRequest)}
     */
    @Test
    void testAddCourse() throws Exception {
        //Arrange
        CourseSaveResponse course = new CourseSaveResponse();
        course.setId(123L);
        course.setTitle("Dr");
        course.setShortDescription("?");
        course.setLongDescription("?");
        course.setImageUrl("https://example.org/example");
        course.setRating(10.0);
        course.setLengthInMinutes(3);
        course.setPrice(10.0);
        course.setCreationDate(null);
        course.setLastUpdateDate(null);
        course.setCategories(new ArrayList<>());
        course.setChapters(new ArrayList<>());
        course.setNumberOfStudents(10);
        course.setLanguage("en");

        String content = (new ObjectMapper()).writeValueAsString(course);

        //Act
        when(coursePaginatedService.saveCourse( any())).thenReturn(course);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/courses/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }

    /**
     * Method under test: {@link CourseController#deleteCourseById(Long)}
     */
    @Test
    void testDeleteCourseById() throws Exception {
        //Act
        when(coursePaginatedService.deleteCourseById((Long) any()))
                .thenReturn(new CourseDeleteResponse("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/courses/delete/{id}", 123L);

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test: {@link CourseController#getAllByCourseIds(CourseIdsRequest)}
     */
    @Test
    void testGetAllByCourseIds() throws Exception {
        //Arrange
        CourseIdsRequest courseIdsRequest = new CourseIdsRequest();
        courseIdsRequest.setCourseIds(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(courseIdsRequest);

        //Act
        when(coursePaginatedService.getAllByCourseIds(any())).thenReturn(new CoursesByIdResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/courses/byids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"courses\":null}"));
    }


    /**
     * Method under test: {@link CourseController#getAlLCategoryNames()}
     */
    @Test
    void testGetAlLCategoryNames() throws Exception {
        //Act
        when(coursePaginatedService.getAllCategoryNames()).thenReturn(new CategoryNameResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courses/category");

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"categoryNames\":null}"));
    }


    /**
     * Method under test: {@link CourseController#getAllCourses(int, int, String, String, Double, Double, String)}
     */
    @Test
    void testGetAllCourses() throws Exception {
        //Act
        when(coursePaginatedService.getAll( any(), any(), any(),  any(),
                 any(), any())).thenReturn(new GetAllResponse());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/courses/get-all");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"courses\":null}"));
    }

    @Test
    void testUpdateCourseById() throws Exception {
        //Arrange
        CourseUpdateResponse course = new CourseUpdateResponse();
        course.setId(123L);
        course.setTitle("Dr");
        course.setShortDescription("?");
        course.setLongDescription("?");
        course.setImageUrl("https://example.org/example");
        course.setRating(10.0);
        course.setLengthInMinutes(3);
        course.setPrice(10.0);
        course.setCreationDate(null);
        course.setLastUpdateDate(null);
        course.setCategories(new ArrayList<>());
        course.setChapters(new ArrayList<>());
        course.setNumberOfStudents(10);
        course.setLanguage("en");

        String content = (new ObjectMapper()).writeValueAsString(course);

        //Act
        when(coursePaginatedService.updateCourseById( any())).thenReturn(course);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/courses/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }
}

