package com.simplyedu.Purchases.repository;

import com.simplyedu.Purchases.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("purchaseRepository")
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Boolean existsByUserIdAndCourseId(Long userId, Long courseId);
    Boolean existsByCourseIdAndUserId(Long courseId, Long userId);
    List<Purchase> getAllByUserId(Long userId); 
    
    int countPurchaseByPurchaseDateStartingWithAndPurchaseDateEndingWith(LocalDate start, LocalDate end);
}
