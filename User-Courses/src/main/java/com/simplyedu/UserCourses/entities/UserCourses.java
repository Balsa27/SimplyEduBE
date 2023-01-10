package com.simplyedu.UserCourses.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCourses {
    private Long userId;
    private Page<CourseItem> courseItems;
}
