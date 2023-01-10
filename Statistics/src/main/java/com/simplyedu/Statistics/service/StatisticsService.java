package com.simplyedu.Statistics.service;

import com.simplyedu.Statistics.entities.Response.ComplexPurchaseResponse;
import com.simplyedu.Statistics.entities.Response.DetailedStatisticsResponse;
import com.simplyedu.Statistics.entities.Response.StatisticsResponse;
import com.simplyedu.Statistics.entities.StatisticsDetailed;

import java.time.LocalDate;

public interface StatisticsService {
    StatisticsResponse getStatistics();
    ComplexPurchaseResponse getDetailedStatistics(StatisticsDetailed statisticsDetailed);
}
