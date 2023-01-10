package com.simplyedu.Subscription.repository;

import com.simplyedu.Subscription.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository("subscriptionRepository")
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Boolean existsByUserId(Long userId);
    Optional<Subscription> findByUserId(Long userId);
//    @Query("SELECT COUNT (s) FROM Subscription s WHERE s.expirationDate <= ?1 and s.startDate >= ?1")
//    Long countTotalActiveSubscriptions(LocalDate currentDate);
//
//    @Query("SELECT COUNT (s) FROM Subscription s WHERE s.expirationDate > ?1 or s.startDate < ?1")
//    Long countTotalInactiveSubscriptions(LocalDate currentDate);
}
