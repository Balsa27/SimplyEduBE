package com.simplyedu.Courses.controllers;

import com.simplyedu.Courses.entities.response.CourseUpdateResponse;
import com.simplyedu.Courses.entities.request.CourseAddRequest;
import com.simplyedu.Courses.entities.request.CourseIdsRequest;
import com.simplyedu.Courses.entities.request.CourseUpdateRequest;
import com.simplyedu.Courses.entities.response.*;
import com.simplyedu.Courses.services.CoursePaginatedService;
import com.simplyedu.Courses.utils.CourseMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController()   
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class CourseController {
    
    private CourseMapper courseMapper = new CourseMapper();
    private final CoursePaginatedService courseService;

    public CourseController(@Qualifier("paginatedService") CoursePaginatedService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/course/{id}") 
    public ResponseEntity<CourseByIdResponse> getCourseById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
    
    @DeleteMapping("/courses/delete/{id}") //ADMIN
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<CourseDeleteResponse> deleteCourseById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(courseService.deleteCourseById(id));
    }

    @PutMapping("/courses/update")  
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<CourseUpdateResponse> updateCourseById(@RequestBody CourseUpdateRequest course){
        return ResponseEntity.ok(courseService.updateCourseById(courseMapper.courseUpdateRequestToCourse(course)));
    }

    @GetMapping("/courses/get-all")
    public ResponseEntity<GetAllResponse> getAllCourses(@RequestParam(defaultValue = "0")int page,
                                                        @RequestParam(defaultValue = "9")int size,
                                                        @RequestParam(required = false)String category,
                                                        @RequestParam(required = false)String query,
                                                        @RequestParam(required = false)Double minPrice,
                                                        @RequestParam(required = false)Double maxPrice,
                                                        @RequestParam(required = false)String sort){
        return ResponseEntity.of(Optional.of(courseService.getAll(PageRequest.of(page, size), category, query, minPrice, maxPrice, sort)));
    }

    @PostMapping("/courses/add") //ADMIN
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<CourseSaveResponse> addCourse(@RequestBody CourseAddRequest course){
        return ResponseEntity.ok(courseService.saveCourse(courseMapper.courseAddRequestToCourse(course)));
    }

    @GetMapping("/courses/category")
    public ResponseEntity<CategoryNameResponse> getAlLCategoryNames(){
        return ResponseEntity.ok(courseService.getAllCategoryNames());
    }
    
    @PostMapping("/courses/byids")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<CoursesByIdResponse> getAllByCourseIds(@RequestBody CourseIdsRequest courseIds){
        return ResponseEntity.ok(courseService.getAllByCourseIds(courseIds.getCourseIds()));
    }
}
