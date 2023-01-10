package com.simplyedu.Purchases.entities.response;

import com.simplyedu.Purchases.http.Course;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetUserPurchasedCoursesResonse {
    private List<Course> purchasedCourses;
}
