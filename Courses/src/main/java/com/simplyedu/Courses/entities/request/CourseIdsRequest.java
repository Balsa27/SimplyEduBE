package com.simplyedu.Courses.entities.request;

import lombok.*;

import java.util.List;

@Data   
@AllArgsConstructor
@NoArgsConstructor
public class CourseIdsRequest {
    private List<Long> courseIds;
}
