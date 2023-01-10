package com.simplyedu.UserCourses.http.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Chapter {
    private Long id;
    private String title;
    private String description;
    private int durationInMinutes;
}

