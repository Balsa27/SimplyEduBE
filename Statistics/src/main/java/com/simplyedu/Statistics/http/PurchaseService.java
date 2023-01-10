package com.simplyedu.Statistics.http;

import com.simplyedu.Statistics.config.FeignConfig.FeignConfig;
import com.simplyedu.Statistics.entities.Response.DetailedStatisticsResponse;
import com.simplyedu.Statistics.entities.Response.PurchaseStatisticsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "PURCHASES", configuration = {
        FeignConfig.class
})
public interface PurchaseService {
    @GetMapping("purchases/statistics")
    PurchaseStatisticsResponse getPurchaseStatistics();
    
    @GetMapping("purchases/detailed-statistics")
    DetailedStatisticsResponse getDetailedPurchaseStatistics();
}
