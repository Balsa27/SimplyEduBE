package com.simplyedu.Statistics.entities.Request;

import com.simplyedu.Statistics.entities.StatisticsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private StatisticsType statisticsType;
}   
