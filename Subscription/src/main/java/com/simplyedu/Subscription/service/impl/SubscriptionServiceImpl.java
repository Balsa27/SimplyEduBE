package com.simplyedu.Subscription.service.impl;

import com.simplyedu.Subscription.Config.Jwt.AuthUser;
import com.simplyedu.Subscription.entities.Subscription;
import com.simplyedu.Subscription.exception.SubscribeException;
import com.simplyedu.Subscription.entities.Response.SubscriptionResponse;
import com.simplyedu.Subscription.entities.Response.SubscriptionStatisticsResponse;
import com.simplyedu.Subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {
    
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(@Qualifier("subscriptionRepository")
                               SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
    
    @Override
    public SubscriptionResponse subscribe() {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
        Optional<Subscription> userSubscription = subscriptionRepository.findByUserId(user.getId());
        
        if(userSubscription.isPresent() && userSubscription.get().getExpirationDate().isAfter(LocalDate.now()))
            throw new SubscribeException("You already have an active subscription");
        
        subscriptionRepository.save(Subscription.builder()
                .userId(user.getId())
                .startDate(LocalDate.now())
                .expirationDate(LocalDate.now().plusMonths(1))
                .isActive(true)
                .build());

        return new SubscriptionResponse("You have successfully subscribed");
    }

    @Override
    public Boolean isSubscribed() {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return subscriptionRepository.existsByUserId(user.getId());
    }
    
    @Override
    public SubscriptionStatisticsResponse getSubscriptionStatistics() {
       
        
        return new SubscriptionStatisticsResponse(subscriptionRepository.findAll());        
    }   
}
