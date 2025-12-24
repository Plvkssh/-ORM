package com.example.lms.repository;

import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByLesson(Lesson lesson);
}
