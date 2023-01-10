package com.simplyedu.UserCourses.entities.response;

import com.simplyedu.UserCourses.http.model.response.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserEnrolledCourseResponse {
    Course course;
    double rating;  
}
