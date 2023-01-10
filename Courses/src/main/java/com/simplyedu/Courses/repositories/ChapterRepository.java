package com.simplyedu.Courses.repositories;

import com.simplyedu.Courses.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ChapterRepository")
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
