package com.simplyedu.Purchases.entities.response;

import com.simplyedu.Purchases.http.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoursesByIdResponse{
    List<Course> courses;
}
