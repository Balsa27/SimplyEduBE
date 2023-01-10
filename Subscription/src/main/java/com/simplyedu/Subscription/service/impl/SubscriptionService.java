package com.simplyedu.Subscription.service.impl;

import com.simplyedu.Subscription.entities.Response.SubscriptionResponse;
import com.simplyedu.Subscription.entities.Response.SubscriptionStatisticsResponse;

public interface SubscriptionService {
    SubscriptionResponse subscribe();
    Boolean isSubscribed();
    SubscriptionStatisticsResponse getSubscriptionStatistics();
}
