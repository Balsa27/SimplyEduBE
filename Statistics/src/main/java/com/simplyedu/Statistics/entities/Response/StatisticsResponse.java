package com.simplyedu.Statistics.entities.Response;

import com.google.inject.BindingAnnotation;
import com.simplyedu.Statistics.entities.Statistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponse {
    Long totalActiveSubscriptions;
    Long totalInactiveSubscriptions;
    Long totalPurchases;
    String leadingIncomeSource;
}
