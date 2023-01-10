package com.simplyedu.UserCourses.http.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Course {
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
    private List<Chapter> chapters = new ArrayList<>();
    private List<Chapter> categories = new ArrayList<>();
    private int numberOfStudents;
    private String language;

}
