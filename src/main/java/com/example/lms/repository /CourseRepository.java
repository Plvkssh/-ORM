package com.example.lms.repository;

import com.example.lms.model.Course;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
    List<Course> findByTeacher(User teacher);
    
    List<Course> findByCategory_Name(String name);
}
