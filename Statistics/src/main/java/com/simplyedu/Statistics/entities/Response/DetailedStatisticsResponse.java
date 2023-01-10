package com.simplyedu.Statistics.entities.Response;

import com.simplyedu.Statistics.entities.Purchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedStatisticsResponse {
    List<Purchase> allPurchases;
}
