package com.simplyedu.Subscription.controller;

import com.simplyedu.Subscription.entities.Response.SubscriptionResponse;
import com.simplyedu.Subscription.entities.Response.SubscriptionStatisticsResponse;
import com.simplyedu.Subscription.service.impl.SubscriptionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(
            @Qualifier("subscriptionService") SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    @PostMapping("/subscription/subscribe")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<SubscriptionResponse> subscribe() {
        return ResponseEntity.ok(subscriptionService.subscribe());
    }
    
    @GetMapping("/subscription/is-subscribed")
    public ResponseEntity<Boolean> isSubscribed() {
        return ResponseEntity.ok(subscriptionService.isSubscribed());
    }
    
    @GetMapping("/subscription/statistics")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<SubscriptionStatisticsResponse> getSubscriptionStatistics() {
        return ResponseEntity.ok(subscriptionService.getSubscriptionStatistics());
    }
}
