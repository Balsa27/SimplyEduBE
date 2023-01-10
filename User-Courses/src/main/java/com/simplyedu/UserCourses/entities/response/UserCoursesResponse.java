package com.simplyedu.UserCourses.entities.response;

import com.simplyedu.UserCourses.entities.CourseItem;
import com.simplyedu.UserCourses.entities.UserCourses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
public class UserCoursesResponse  {
    private Long userId;
    private Page<CourseItem> courseItems;
}
