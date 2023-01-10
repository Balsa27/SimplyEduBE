package com.simplyedu.Statistics.util;

import com.simplyedu.Statistics.entities.Request.DetailedRequest;
import com.simplyedu.Statistics.entities.Response.DetailedStatisticsResponse;
import com.simplyedu.Statistics.entities.StatisticsDetailed;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StatisticsMapper {
    public StatisticsDetailed detailedRequestToDomain(DetailedRequest request){
        return request == null ? null : StatisticsDetailed.builder()
                .start(request.getStartDate())
                .end(request.getEndDate())
                .type(request.getStatisticsType())
                .build();
    }
}
