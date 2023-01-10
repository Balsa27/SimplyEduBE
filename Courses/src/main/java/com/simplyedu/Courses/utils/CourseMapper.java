package com.simplyedu.Courses.utils;
import com.simplyedu.Courses.entities.request.CourseAddRequest;
import com.simplyedu.Courses.entities.request.CourseUpdateRequest;
import com.simplyedu.Courses.entities.response.CourseByIdResponse;
import com.simplyedu.Courses.entities.Course;
import com.simplyedu.Courses.entities.response.CourseSaveResponse;
import com.simplyedu.Courses.entities.response.CourseUpdateResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CourseMapper {
    public CourseByIdResponse courseToCourseByIdResponse(Course courseResponse){
        return new CourseByIdResponse(
                courseResponse.getId(),
                courseResponse.getTitle(),
                courseResponse.getShortDescription(),
                courseResponse.getLongDescription(),
                courseResponse.getImageUrl(),
                courseResponse.getRating(),
                courseResponse.getLengthInMinutes(),
                courseResponse.getPrice(),
                courseResponse.getCreationDate(),
                courseResponse.getLastUpdateDate(),
                courseResponse.getCategories(),
                courseResponse.getChapters(),
                courseResponse.getNumberOfStudents(),
                courseResponse.getLanguage()
        );
    }

    public CourseUpdateResponse courseToCourseUpdateResponse(Course courseResponse){
        return new CourseUpdateResponse(
                courseResponse.getId(),
                courseResponse.getTitle(),
                courseResponse.getShortDescription(),
                courseResponse.getLongDescription(),
                courseResponse.getImageUrl(),
                courseResponse.getRating(),
                courseResponse.getLengthInMinutes(),
                courseResponse.getPrice(),
                courseResponse.getCreationDate(),
                courseResponse.getLastUpdateDate(),
                courseResponse.getCategories(),
                courseResponse.getChapters(),
                courseResponse.getNumberOfStudents(),
                courseResponse.getLanguage()
        );
    }

    public CourseSaveResponse courseToCourseSaveResponse(Course courseResponse){
        return new CourseSaveResponse(
                courseResponse.getId(),
                courseResponse.getTitle(),
                courseResponse.getShortDescription(),
                courseResponse.getLongDescription(),
                courseResponse.getImageUrl(),
                courseResponse.getRating(),
                courseResponse.getLengthInMinutes(),
                courseResponse.getPrice(),
                courseResponse.getCreationDate(),
                courseResponse.getLastUpdateDate(),
                courseResponse.getCategories(),
                courseResponse.getChapters(),
                courseResponse.getNumberOfStudents(),
                courseResponse.getLanguage()
        );
    }
    
    public Course courseUpdateRequestToCourse(CourseUpdateRequest courseUpdateRequest){
        return new Course(
                courseUpdateRequest.getId(),
                courseUpdateRequest.getTitle(),
                courseUpdateRequest.getShortDescription(),
                courseUpdateRequest.getLongDescription(),
                courseUpdateRequest.getImageUrl(),
                courseUpdateRequest.getRating(),
                courseUpdateRequest.getLengthInMinutes(),
                courseUpdateRequest.getPrice(),
                courseUpdateRequest.getCreationDate(),
                courseUpdateRequest.getLastUpdateDate(),
                courseUpdateRequest.getCategories(),
                courseUpdateRequest.getChapters(),
                courseUpdateRequest.getNumberOfStudents(),
                courseUpdateRequest.getLanguage()
        );
    }

    public Course courseAddRequestToCourse(CourseAddRequest courseUpdateRequest){
        return new Course(
                courseUpdateRequest.getId(),
                courseUpdateRequest.getTitle(),
                courseUpdateRequest.getShortDescription(),
                courseUpdateRequest.getLongDescription(),
                courseUpdateRequest.getImageUrl(),
                courseUpdateRequest.getRating(),
                courseUpdateRequest.getLengthInMinutes(),
                courseUpdateRequest.getPrice(),
                courseUpdateRequest.getCreationDate(),
                courseUpdateRequest.getLastUpdateDate(),
                courseUpdateRequest.getCategories(),
                courseUpdateRequest.getChapters(),
                courseUpdateRequest.getNumberOfStudents(),
                courseUpdateRequest.getLanguage()
        );
    }
}
