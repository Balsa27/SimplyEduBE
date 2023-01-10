package com.simplyedu.Cart.http.entities.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data   
@AllArgsConstructor
@NoArgsConstructor
public class CourseIdsRequest {
    private List<Long> courseIds;
}
