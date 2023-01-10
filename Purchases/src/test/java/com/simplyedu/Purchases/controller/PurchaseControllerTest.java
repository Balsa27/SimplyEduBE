package com.simplyedu.Purchases.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplyedu.Purchases.entities.request.PurchaseRequest;
import com.simplyedu.Purchases.entities.response.PurchaseResponse;
import com.simplyedu.Purchases.entities.response.PurchaseStatisticsResponse;
import com.simplyedu.Purchases.http.CourseService;
import com.simplyedu.Purchases.repository.PurchaseRepository;
import com.simplyedu.Purchases.service.PurchaseService;
import com.simplyedu.Purchases.service.impl.PurchaseServiceImpl;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PurchaseController.class})
@ExtendWith(SpringExtension.class)
class PurchaseControllerTest {
    @Autowired
    private PurchaseController purchaseController;

    @MockBean(name = "purchaseService")
    private PurchaseService purchaseService;

    /**
     * Method under test: {@link PurchaseController#purchaseCourses(PurchaseRequest)}
     */
    @Test
    void purchaseCourses_pass() throws Exception {
        //Arrange
        PurchaseController purchaseController = new PurchaseController(
                new PurchaseServiceImpl(mock(PurchaseRepository.class), mock(CourseService.class)));
        PurchaseRequest purchaseRequest = mock(PurchaseRequest.class);
        ResponseEntity<PurchaseResponse> actualPurchaseCoursesResult = purchaseController
                .purchaseCourses(purchaseRequest);
        
        //Act
        when(purchaseRequest.getCourseIds()).thenReturn(new HashSet<>());
        
        //Assert
        assertTrue(actualPurchaseCoursesResult.hasBody());
        assertTrue(actualPurchaseCoursesResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualPurchaseCoursesResult.getStatusCode());
        assertTrue(actualPurchaseCoursesResult.getBody().getCoursePurchaseResponse().isEmpty());
        verify(purchaseRequest).getCourseIds();
    }


    /**
     * Method under test: {@link PurchaseController#isPurchased(Long)}
     */
    @Test
    void isPurchased_pass() throws Exception {
        //Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/purchases/is-purchased/{courseId}",
                123L);
        
        //Act
        when(purchaseService.isPurchased(any())).thenReturn(true);
       
       //Assert
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(purchaseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link PurchaseController#getUserPurchasedCourses()}
     */
    @Test
    void getUserPurchasedCourses_pass() throws Exception {
        //Arrange
        PurchaseRequest purchaseRequest = new PurchaseRequest(1L, new HashSet<>());
        PurchaseResponse purchaseResponse = new PurchaseResponse(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(purchaseRequest);
        
        //Act
        Mockito.when(purchaseService.purchaseCourses(any())).thenReturn(purchaseResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/purchases/purchased")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(purchaseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
             
    }
}

