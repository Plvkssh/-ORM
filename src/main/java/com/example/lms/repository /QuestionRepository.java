package com.example.lms.repository;

import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для управления вопросами тестов.
 * Вопросы являются составными элементами тестов и содержат варианты ответов.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    /**
     * Находит все вопросы, принадлежащие указанному тесту.
     * Используется для построения теста и отображения всех его вопросов.
     *
     * @param quiz тест, для которого нужно найти вопросы
     * @return список вопросов теста
     */
    List<Question> findByQuiz(Quiz quiz);
}
