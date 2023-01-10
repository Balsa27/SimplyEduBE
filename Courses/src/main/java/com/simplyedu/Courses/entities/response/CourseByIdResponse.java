package com.simplyedu.Courses.entities.response;
import com.simplyedu.Courses.entities.Category;
import com.simplyedu.Courses.entities.Chapter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class CourseByIdResponse extends CourseResponse {
    public CourseByIdResponse(
            Long id,
            String title,
            String shortDescription,
            String longDescription, 
            String imageUrl,
            double rating, 
            int lengthInMinutes,
            double price,
            LocalDate creationDate,
            LocalDate lastUpdateDate,
            List<Category> categories,
            List<Chapter> chapters,
            int numberOfStudents, 
            String language) {
        super(id,
                title,
                shortDescription,
                longDescription,
                imageUrl, rating,
                lengthInMinutes, 
                price, creationDate,
                lastUpdateDate,
                categories,
                chapters,
                numberOfStudents,
                language);
    }
}
