package com.simplyedu.Statistics.controller;

import com.simplyedu.Statistics.entities.Request.DetailedRequest;
import com.simplyedu.Statistics.entities.Response.ComplexPurchaseResponse;
import com.simplyedu.Statistics.entities.Response.DetailedStatisticsResponse;
import com.simplyedu.Statistics.entities.Response.StatisticsResponse;
import com.simplyedu.Statistics.service.StatisticsService;
import com.simplyedu.Statistics.util.StatisticsMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticsController {
    
    private StatisticsMapper mapper = new StatisticsMapper();
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics/all")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<StatisticsResponse> getAllStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
    
    @GetMapping("/statistics/detailed")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<ComplexPurchaseResponse> getDetailedPurchases(@RequestBody DetailedRequest detailedRequest) {
        return ResponseEntity.ok(statisticsService.getDetailedStatistics(mapper.detailedRequestToDomain(detailedRequest)));
    }
}
