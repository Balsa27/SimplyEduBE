package com.simplyedu.Cart.http.entities.response;

import com.simplyedu.Cart.http.Category;
import com.simplyedu.Cart.http.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseByIdResponse {
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
    private List<Category> categories;
    private List<Chapter> chapters;
    private int numberOfStudents;
    private String language;
}
