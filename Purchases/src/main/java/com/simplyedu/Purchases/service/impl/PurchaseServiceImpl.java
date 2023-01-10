package com.simplyedu.Purchases.service.impl;



import com.simplyedu.Purchases.config.Jwt.AuthUser;
import com.simplyedu.Purchases.entities.CoursePurchase;
import com.simplyedu.Purchases.entities.request.CourseIdsRequest;
import com.simplyedu.Purchases.entities.request.PurchaseRequest;
import com.simplyedu.Purchases.entities.response.*;
import com.simplyedu.Purchases.exception.MalformedRequestException;
import com.simplyedu.Purchases.http.CourseService;
import com.simplyedu.Purchases.entities.Purchase;
import com.simplyedu.Purchases.http.Course;
import com.simplyedu.Purchases.repository.PurchaseRepository;
import com.simplyedu.Purchases.service.PurchaseService;
import com.simplyedu.Purchases.util.PurchaseMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {
    private PurchaseMapper purchaseMapper = new PurchaseMapper();
    private final PurchaseRepository purchaseRepository;
    private final CourseService courseService;
    
    public PurchaseServiceImpl(@Qualifier("purchaseRepository") 
                               PurchaseRepository purchaseRepository,
                               CourseService courseService) {
        this.purchaseRepository = purchaseRepository;
        this.courseService = courseService;
    }

    @Override
    public PurchaseResponse purchaseCourses(PurchaseRequest request) { //coming from a different ms
        Set<Long> courseIds = request.getCourseIds();
        
        if(courseIds == null)
            throw new MalformedRequestException("No courses to purchase");
        
        Set<CoursePurchase> responses = courseIds.stream().map(courseId -> {
            if(purchaseRepository.existsByUserIdAndCourseId(request.getUserId(), courseId))
                return CoursePurchase.builder()
                        .courseId(courseId)
                        .responseMessage("Course already purchased")
                        .build();
            else{
               purchaseRepository.save(Purchase.builder() //saving directly to db, no logic necessary for domain
                       .userId(request.getUserId())
                       .courseId(courseId)
                       .purchaseDate(LocalDate.now())
                       .build());
               
                return CoursePurchase.builder()
                        .courseId(courseId)
                        .responseMessage("Course purchased successfully")
                        .build();
            }
        }).collect(Collectors.toSet());
        
        return PurchaseResponse.builder()
                .coursePurchaseResponse(responses)
                .build();
    }
    
//    @Override
//    public PurchaseStatisticsResponse getPurchaseStatistics() {
//        Long statistics = purchaseRepository.count();
//        return PurchaseStatisticsResponse.builder()
//                .totalPurchases(statistics)
//                .build();
//    }
    @Override
    public Boolean isPurchased(Long courseId) {     
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return purchaseRepository.existsByCourseIdAndUserId(courseId, user.getId());
    }

    @Override 
    public GetUserPurchasedCoursesResonse getPurchasedCourses() {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Purchase> purchasedCourses = purchaseRepository.getAllByUserId(user.getId());
        
        if(purchasedCourses == null) {
            throw new MalformedRequestException("No purchases found");
        }

        CourseIdsRequest request = CourseIdsRequest.builder()
                .courseIds(purchasedCourses.stream().map(Purchase::getCourseId).toList())
                .build();

        CoursesByIdResponse courses = courseService.getAllByCourseIds(request);
        
        List<Course> coursesToBeReturned =  courses.getCourses();
        
        return GetUserPurchasedCoursesResonse
                .builder()
                .purchasedCourses(coursesToBeReturned)
                .build();
    }

    @Override
    public PurchaseDetailedResponse getAllPurchasesForStatistics() {
        return purchaseMapper.mapPurchaseToPurchaseDetailedResponse(purchaseRepository.findAll());
    }
}
