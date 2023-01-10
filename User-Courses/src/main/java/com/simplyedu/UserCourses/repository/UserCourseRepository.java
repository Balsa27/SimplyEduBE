package com.simplyedu.UserCourses.repository;

import com.simplyedu.UserCourses.entities.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("user-courses-jpa-repository")
public interface UserCourseRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
    Page<Enrollment> findAllByUserId(Long userId, Pageable page);
    //Page<UserEnrolledCourse> findAllByUserIdAndIsPurchasedTrue(Long userId, Pageable page);
}
