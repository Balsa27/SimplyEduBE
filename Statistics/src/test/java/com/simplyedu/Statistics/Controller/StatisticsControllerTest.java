package com.simplyedu.Statistics.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.simplyedu.Statistics.entities.Purchase;
import com.simplyedu.Statistics.entities.Request.DetailedRequest;
import com.simplyedu.Statistics.entities.Response.ComplexPurchaseResponse;
import com.simplyedu.Statistics.entities.Response.DetailedStatisticsResponse;
import com.simplyedu.Statistics.entities.Response.StatisticsResponse;
import com.simplyedu.Statistics.entities.StatisticsDetailed;
import com.simplyedu.Statistics.entities.StatisticsType;
import com.simplyedu.Statistics.http.PurchaseService;
import com.simplyedu.Statistics.http.SubscriptionService;
import com.simplyedu.Statistics.service.Impl.StatisticsServiceImpl;
import com.simplyedu.Statistics.service.StatisticsService;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StatisticsController.class})
@ExtendWith(SpringExtension.class)
class StatisticsControllerTest {
    @Autowired
    private StatisticsController statisticsController;

    @MockBean
    private StatisticsService statisticsService;

    /**
     * Method under test: {@link StatisticsController#getAllStatistics()}
     */
    @Test
    void testGetAllStatistics() throws Exception {
        //Act
        when(statisticsService.getStatistics()).thenReturn(new StatisticsResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statistics/all");

        //Assert
        MockMvcBuilders.standaloneSetup(statisticsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    /**
     * Method under test: {@link StatisticsController#getAllStatistics()}
     */
    @Test
    void testGetAllStatistics2() throws Exception {
        when(statisticsService.getStatistics()).thenReturn(new StatisticsResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statistics/all");
        MockMvcBuilders.standaloneSetup(statisticsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"totalActiveSubscriptions\":null,\"totalInactiveSubscriptions\":null,\"totalPurchases\":null,\"leadingIncomeSource"
                                        + "\":null}"));
    }

}
