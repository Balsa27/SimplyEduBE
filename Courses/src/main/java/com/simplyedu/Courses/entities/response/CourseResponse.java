package com.simplyedu.Courses.entities.response;

import com.simplyedu.Courses.entities.Category;
import com.simplyedu.Courses.entities.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class CourseResponse {
    private Long id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private double rating;
    private int lengthInMinutes;
    private double price;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private List<Category> categories = new ArrayList<>();
    private List<Chapter> chapters = new ArrayList<>();
    private int numberOfStudents;
    private String language;
}
