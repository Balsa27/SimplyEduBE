package com.simplyedu.Statistics.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.simplyedu.Statistics.entities.Purchase;
import com.simplyedu.Statistics.entities.Response.DetailedStatisticsResponse;
import com.simplyedu.Statistics.entities.Response.StatisticsResponse;
import com.simplyedu.Statistics.entities.Response.SubscriptionStatisticsResponse;
import com.simplyedu.Statistics.entities.StatisticsDetailed;
import com.simplyedu.Statistics.entities.StatisticsType;
import com.simplyedu.Statistics.entities.Subscription;
import com.simplyedu.Statistics.http.PurchaseService;
import com.simplyedu.Statistics.http.SubscriptionService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StatisticsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StatisticsServiceImplTest {
    @MockBean
    private PurchaseService purchaseService;

    @Autowired
    private StatisticsServiceImpl statisticsServiceImpl;

    @MockBean
    private SubscriptionService subscriptionService;


    /**
     * Method under test: {@link StatisticsServiceImpl#getStatistics()}
     */
    @Test
    void testGetStatistics_pass1() {
        //Arrange
        DetailedStatisticsResponse detailedStatisticsResponse = new DetailedStatisticsResponse();
        SubscriptionStatisticsResponse subscriptionStatisticsResponse = new SubscriptionStatisticsResponse();
        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();
        Purchase purchase = new Purchase(1L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase2 = new Purchase(2L, 2L, 2L, LocalDate.of(1969, 1, 1));
        purchaseList.add(purchase);
        purchaseList.add(purchase2);
        ArrayList<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription(1L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), true);
        Subscription subscription2 = new Subscription(2L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), false);
        subscriptionList.add(subscription);
        subscriptionList.add(subscription2);
        subscriptionStatisticsResponse.setAllSubscriptions(subscriptionList);
        detailedStatisticsResponse.setAllPurchases(purchaseList);
        StatisticsResponse statisticsResponse = new StatisticsResponse();

        //Act
        when(purchaseService.getDetailedPurchaseStatistics()).thenReturn(detailedStatisticsResponse);
        when(subscriptionService.getSubscriptionStatistics()).thenReturn(subscriptionStatisticsResponse);

        //Assert
        assertEquals(2, (long) statisticsServiceImpl.getStatistics().getTotalPurchases());
        assertEquals(1, (long) statisticsServiceImpl.getStatistics().getTotalActiveSubscriptions());
        assertEquals(1, (long) statisticsServiceImpl.getStatistics().getTotalInactiveSubscriptions());
        assertEquals("Currently Subscriptions and Purchases bring equal income.", statisticsServiceImpl.getStatistics().getLeadingIncomeSource());

    }

    /**
     * Method under test: {@link StatisticsServiceImpl#getStatistics()}
     */
    @Test
    void testGetStatistics_pass2() {
        //Arrange
        DetailedStatisticsResponse detailedStatisticsResponse = new DetailedStatisticsResponse();
        SubscriptionStatisticsResponse subscriptionStatisticsResponse = new SubscriptionStatisticsResponse();
        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();
        Purchase purchase = new Purchase(1L, 1L, 1L, LocalDate.of(1970, 1, 1));
        purchaseList.add(purchase);
        ArrayList<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription(1L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), true);
        Subscription subscription2 = new Subscription(2L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), false);
        Subscription subscription3 = new Subscription(2L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), false);
        Subscription subscription4 = new Subscription(2L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), false);
        subscriptionList.add(subscription);
        subscriptionList.add(subscription2);
        subscriptionList.add(subscription3);
        subscriptionList.add(subscription4);
        subscriptionStatisticsResponse.setAllSubscriptions(subscriptionList);
        detailedStatisticsResponse.setAllPurchases(purchaseList);
        StatisticsResponse statisticsResponse = new StatisticsResponse();

        //Act
        when(purchaseService.getDetailedPurchaseStatistics()).thenReturn(detailedStatisticsResponse);
        when(subscriptionService.getSubscriptionStatistics()).thenReturn(subscriptionStatisticsResponse);

        //Assert
        assertEquals(1, (long) statisticsServiceImpl.getStatistics().getTotalPurchases());
        assertEquals(1, (long) statisticsServiceImpl.getStatistics().getTotalActiveSubscriptions());
        assertEquals(3, (long) statisticsServiceImpl.getStatistics().getTotalInactiveSubscriptions());
        assertEquals("Subscriptions are in the lead by having 80% out of total income", statisticsServiceImpl.getStatistics().getLeadingIncomeSource());

    }

    /**
     * Method under test: {@link StatisticsServiceImpl#getStatistics()}
     */
    @Test
    void testGetStatistics_pass3() {
        //Arrange
        DetailedStatisticsResponse detailedStatisticsResponse = new DetailedStatisticsResponse();
        SubscriptionStatisticsResponse subscriptionStatisticsResponse = new SubscriptionStatisticsResponse();
        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();
        Purchase purchase = new Purchase(1L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase2 = new Purchase(5L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase3 = new Purchase(4L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase4 = new Purchase(2L, 1L, 1L, LocalDate.of(1970, 1, 1));
        purchaseList.add(purchase);
        purchaseList.add(purchase2);
        purchaseList.add(purchase3);
        purchaseList.add(purchase4);
        ArrayList<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription(1L, 1L, LocalDate.of(1930, 1, 1), LocalDate.of(1970, 1, 1), true);
        subscriptionList.add(subscription);
        subscriptionStatisticsResponse.setAllSubscriptions(subscriptionList);
        detailedStatisticsResponse.setAllPurchases(purchaseList);
        StatisticsResponse statisticsResponse = new StatisticsResponse();

        //Act
        when(purchaseService.getDetailedPurchaseStatistics()).thenReturn(detailedStatisticsResponse);
        when(subscriptionService.getSubscriptionStatistics()).thenReturn(subscriptionStatisticsResponse);

        //Assert
        assertTrue(statisticsServiceImpl.getStatistics().getTotalPurchases() == 4);
        assertTrue(statisticsServiceImpl.getStatistics().getTotalActiveSubscriptions() == 1);
        assertTrue(statisticsServiceImpl.getStatistics().getTotalInactiveSubscriptions() == 0);
        assertTrue(Objects.equals(statisticsServiceImpl.getStatistics().getLeadingIncomeSource(), "Purchases are in the lead by having 80% out of total income"));

    }


    /**
     * Method under test: {@link StatisticsServiceImpl#getDetailedStatistics(StatisticsDetailed)}
     */
    @Test
    void testGetDetailedStatistics_pass1() {
        //Arrange
        StatisticsDetailed statisticsDetailed = new StatisticsDetailed();
        statisticsDetailed.setStart(LocalDate.of(1930, 1, 1));
        statisticsDetailed.setEnd(LocalDate.of(1971, 1, 1));
        statisticsDetailed.setType(StatisticsType.YEARLY);

        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();
        Purchase purchase = new Purchase(1L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase2 = new Purchase(5L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase3 = new Purchase(4L, 1L, 1L, LocalDate.of(1970, 1, 1));
        Purchase purchase4 = new Purchase(2L, 1L, 1L, LocalDate.of(1970, 1, 1));
        purchaseList.add(purchase);
        purchaseList.add(purchase2);
        purchaseList.add(purchase3);
        purchaseList.add(purchase4);

        DetailedStatisticsResponse detailedStatisticsResponse = new DetailedStatisticsResponse();
        detailedStatisticsResponse.setAllPurchases(purchaseList);

        //Act
        Mockito.when(purchaseService.getDetailedPurchaseStatistics()).thenReturn(detailedStatisticsResponse);

        //Assert
        assertEquals(4, statisticsServiceImpl.getDetailedStatistics(statisticsDetailed).getAggregatedPurchaseStatistic());
    }

    /**
     * Method under test: {@link StatisticsServiceImpl#getDetailedStatistics(StatisticsDetailed)}
     */
    @Test
    void testGetDetailedStatistics_pass2() {
        //Arrange
        StatisticsDetailed statisticsDetailed = new StatisticsDetailed();
        statisticsDetailed.setStart(LocalDate.of(1930, 1, 1));
        statisticsDetailed.setEnd(LocalDate.of(1971, 3, 1));
        statisticsDetailed.setType(StatisticsType.MONTHLY);

        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();
        Purchase purchase = new Purchase(1L, 1L, 1L, LocalDate.of(2022, 2, 1));
        Purchase purchase2 = new Purchase(5L, 1L, 1L, LocalDate.of(2022, 2, 1));
        Purchase purchase3 = new Purchase(4L, 1L, 1L, LocalDate.of(2022, 2, 1));
        Purchase purchase4 = new Purchase(2L, 1L, 1L, LocalDate.of(2022, 2, 1));
        purchaseList.add(purchase);
        purchaseList.add(purchase2);
        purchaseList.add(purchase3);
        purchaseList.add(purchase4);

        DetailedStatisticsResponse detailedStatisticsResponse = new DetailedStatisticsResponse();
        detailedStatisticsResponse.setAllPurchases(purchaseList);

        //Act
        Mockito.when(purchaseService.getDetailedPurchaseStatistics()).thenReturn(detailedStatisticsResponse);

        //Assert
        assertEquals(4, statisticsServiceImpl.getDetailedStatistics(statisticsDetailed).getAggregatedPurchaseStatistic());
    }

    /**
     * Method under test: {@link StatisticsServiceImpl#getDetailedStatistics(StatisticsDetailed)}
     */
    @Test
    void testGetDetailedStatistics_pass3() {
        //Arrange
        StatisticsDetailed statisticsDetailed = new StatisticsDetailed();
        statisticsDetailed.setStart(LocalDate.of(1930, 1, 1));
        statisticsDetailed.setEnd(LocalDate.of(1971, 3, 1));
        statisticsDetailed.setType(StatisticsType.DAILY);

        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();
        Purchase purchase = new Purchase(1L, 1L, 1L, LocalDate.of(2022, 2, 1));
        Purchase purchase2 = new Purchase(5L, 1L, 1L, LocalDate.of(2022, 2, 1));
        Purchase purchase3 = new Purchase(4L, 1L, 1L, LocalDate.of(2022, 2, 1));
        Purchase purchase4 = new Purchase(2L, 1L, 1L, LocalDate.of(2022, 2, 1));
        purchaseList.add(purchase);
        purchaseList.add(purchase2);
        purchaseList.add(purchase3);
        purchaseList.add(purchase4);

        DetailedStatisticsResponse detailedStatisticsResponse = new DetailedStatisticsResponse();
        detailedStatisticsResponse.setAllPurchases(purchaseList);

        //Act
        Mockito.when(purchaseService.getDetailedPurchaseStatistics()).thenReturn(detailedStatisticsResponse);

        //Assert
        assertEquals(4, statisticsServiceImpl.getDetailedStatistics(statisticsDetailed).getAggregatedPurchaseStatistic());
    }
}