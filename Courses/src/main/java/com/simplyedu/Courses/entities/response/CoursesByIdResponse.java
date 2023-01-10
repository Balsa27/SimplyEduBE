package com.simplyedu.Courses.entities.response;
import com.simplyedu.Courses.entities.Course;
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
