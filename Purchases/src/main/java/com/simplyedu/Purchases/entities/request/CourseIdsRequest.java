package com.simplyedu.Purchases.entities.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data   
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseIdsRequest {
    private List<Long> courseIds;
}
