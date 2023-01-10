package com.simplyedu.Statistics.entities.Response;

import com.simplyedu.Statistics.entities.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionStatisticsResponse {
    List<Subscription> allSubscriptions = new ArrayList<>();
}
