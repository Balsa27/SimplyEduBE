package com.simplyedu.UserCourses.entities.response;

import com.simplyedu.UserCourses.entities.CourseItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindAllCoursesEnrolledByUserIdResponse {
    private Page<CourseItem> courseItems;
}
