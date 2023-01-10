package com.simplyedu.Statistics.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistics {
    Long totalActiveSubscriptions;
    Long totalInactiveSubscriptions;
    Long totalPurchases;
}
