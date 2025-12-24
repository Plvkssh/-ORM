package com.example.lms.repository;

import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findByQuiz(Quiz quiz);
}
