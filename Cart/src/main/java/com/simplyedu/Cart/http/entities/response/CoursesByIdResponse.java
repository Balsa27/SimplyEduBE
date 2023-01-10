package com.simplyedu.Cart.http.entities.response;

import com.simplyedu.Cart.http.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoursesByIdResponse {
    List<Course> courses;
}
