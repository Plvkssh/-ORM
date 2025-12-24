package com.example.lms.repository;

import com.example.lms.model.Lesson;
import com.example.lms.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
    List<Lesson> findByModule(Module module);
}
