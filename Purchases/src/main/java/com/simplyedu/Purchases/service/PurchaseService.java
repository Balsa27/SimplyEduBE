package com.simplyedu.Purchases.service;


import com.simplyedu.Purchases.entities.request.PurchaseDetailedRequest;
import com.simplyedu.Purchases.entities.request.PurchaseRequest;
import com.simplyedu.Purchases.entities.response.GetUserPurchasedCoursesResonse;
import com.simplyedu.Purchases.entities.response.PurchaseDetailedResponse;
import com.simplyedu.Purchases.entities.response.PurchaseResponse;
import com.simplyedu.Purchases.entities.response.PurchaseStatisticsResponse;

import java.time.LocalDate;

public interface PurchaseService {
    PurchaseResponse purchaseCourses(PurchaseRequest request); //coming from different ms
    //PurchaseStatisticsResponse getPurchaseStatistics();
    Boolean isPurchased(Long courseId);
    GetUserPurchasedCoursesResonse getPurchasedCourses();
    PurchaseDetailedResponse getAllPurchasesForStatistics(); //coming from different ms
}
