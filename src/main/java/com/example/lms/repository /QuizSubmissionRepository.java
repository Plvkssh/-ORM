package com.example.lms.repository;

import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    
    List<QuizSubmission> findByQuiz(Quiz quiz);
    
    List<QuizSubmission> findByStudent(User student);
}
