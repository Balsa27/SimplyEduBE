package com.simplyedu.Purchases.controller;

import com.simplyedu.Purchases.entities.request.PurchaseDetailedRequest;
import com.simplyedu.Purchases.entities.request.PurchaseRequest;
import com.simplyedu.Purchases.entities.response.PurchaseDetailedResponse;
import com.simplyedu.Purchases.entities.response.PurchaseResponse;
import com.simplyedu.Purchases.entities.response.PurchaseStatisticsResponse;
import com.simplyedu.Purchases.entities.response.GetUserPurchasedCoursesResonse;
import com.simplyedu.Purchases.service.PurchaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(@Qualifier("purchaseService") PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    
    @PostMapping("/purchases/purchase")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<PurchaseResponse> purchaseCourses(@RequestBody PurchaseRequest request){
        return ResponseEntity.ok(purchaseService.purchaseCourses(request));
    }
    
//    @GetMapping("/purchases/statistics")
//    @RolesAllowed("ROLE_ADMIN")
//    public ResponseEntity<PurchaseStatisticsResponse> getPurchaseStatistics(){
//        return ResponseEntity.ok(purchaseService.getPurchaseStatistics());
//    }
    
    @GetMapping("/purchases/is-purchased/{courseId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Boolean> isPurchased(@PathVariable("courseId") Long courseId){
        return ResponseEntity.ok(purchaseService.isPurchased(courseId));
    }
    
    @GetMapping("/purchases/purchased")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<GetUserPurchasedCoursesResonse> getUserPurchasedCourses(){
        return ResponseEntity.ok(purchaseService.getPurchasedCourses());
    }
    
    @GetMapping("/purchases/detailed-statistics")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<PurchaseDetailedResponse> getDetailedPurchaseStatistics(){
        return ResponseEntity.ok(purchaseService.getAllPurchasesForStatistics());
    }
}
