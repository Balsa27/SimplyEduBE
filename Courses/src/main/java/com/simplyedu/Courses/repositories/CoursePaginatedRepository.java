package com.simplyedu.Courses.repositories;

import com.simplyedu.Courses.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("paginatedRepository")
public interface CoursePaginatedRepository extends JpaRepository<Course, Long> {
    @Query("SELECT DISTINCT name FROM Category")
    List<String> getAllCategoryNames();

    Boolean existsByTitle(String title);
    
    
}
