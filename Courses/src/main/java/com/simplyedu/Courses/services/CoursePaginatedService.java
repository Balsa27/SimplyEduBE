package com.simplyedu.Courses.services;
import com.simplyedu.Courses.entities.Course;
import com.simplyedu.Courses.entities.response.CourseUpdateResponse;
import com.simplyedu.Courses.entities.response.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CoursePaginatedService {

    GetAllResponse getAll(Pageable pageable,
                          String category,
                          String searchQuery,
                          Double minPrice,
                          Double maxPrice,
                          String sortingStrategy);

    CourseByIdResponse getCourseById(Long id);
    CourseDeleteResponse deleteCourseById(Long id);
    CourseUpdateResponse updateCourseById(Course course);
    CourseSaveResponse saveCourse(Course course);
    CategoryNameResponse getAllCategoryNames();
    CoursesByIdResponse getAllByCourseIds(List<Long> courseIds);
    //CourseDeleteResponse addCategoryToCourse(Long courseId, String name);
}
