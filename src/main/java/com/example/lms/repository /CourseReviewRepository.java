package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
    
    List<CourseReview> findByCourse(Course course);
    
    List<CourseReview> findByStudent(User student);
}
