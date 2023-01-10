package com.simplyedu.Courses.entities.response;
import com.simplyedu.Courses.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllResponse {
    Page<Course> courses;
}
