package com.simplyedu.Statistics.http;

import com.simplyedu.Statistics.config.FeignConfig.FeignConfig;
import com.simplyedu.Statistics.entities.Response.SubscriptionStatisticsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "SUBSCRIPTION", configuration = {
        FeignConfig.class
})
public interface SubscriptionService {
    @GetMapping("subscription/statistics")
    SubscriptionStatisticsResponse getSubscriptionStatistics();
}
