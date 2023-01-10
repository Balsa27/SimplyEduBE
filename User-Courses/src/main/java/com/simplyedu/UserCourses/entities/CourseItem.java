package com.simplyedu.UserCourses.entities;

import com.simplyedu.UserCourses.http.model.response.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseItem {
    private Course course;
    private double rating;
}
