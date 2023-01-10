package com.simplyedu.Subscription.entities.Response;

import com.simplyedu.Subscription.entities.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionStatisticsResponse {
    List<Subscription> allSubscrptions;
}
