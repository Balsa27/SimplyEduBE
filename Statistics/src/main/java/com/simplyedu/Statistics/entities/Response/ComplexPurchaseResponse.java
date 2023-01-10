package com.simplyedu.Statistics.entities.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplexPurchaseResponse {
    long aggregatedPurchaseStatistic;
}
