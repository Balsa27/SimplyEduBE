package com.simplyedu.Subscription.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.simplyedu.Subscription.controller.SubscriptionController;
import com.simplyedu.Subscription.entities.Request.SubscriptionRequest;
import com.simplyedu.Subscription.entities.Response.SubscriptionStatisticsResponse;
import com.simplyedu.Subscription.service.impl.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SubscriptionController.class})
@ExtendWith(SpringExtension.class)
class SubscriptionControllerTest {
    @Autowired
    private SubscriptionController subscriptionController;

    @MockBean(name = "subscriptionService")
    private SubscriptionService subscriptionService;

    /**
     * Method under test: {@link SubscriptionController#subscribe}
     */
    @Test
    void subscribe_pass() throws Exception {
        //Arrange
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscription/subscribe");
        
        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(subscriptionController)
                .build()
                .perform(requestBuilder);
        
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link SubscriptionController#isSubscribed()}
     */
    @Test
    void isSubscribed_pass() throws Exception {
        //Act
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/subscription/is-subscribed");
        when(subscriptionService.isSubscribed()).thenReturn(true);
        
        //Assert
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(subscriptionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link SubscriptionController#getSubscriptionStatistics()}
     */
    @Test
    void getSubscriptionStatistics_pass() throws Exception {
        //Act
        when(subscriptionService.getSubscriptionStatistics()).thenReturn(new SubscriptionStatisticsResponse());
        
        //Assert
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/subscription/statistics");
        MockMvcBuilders.standaloneSetup(subscriptionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}

