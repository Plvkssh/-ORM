package com.example.lms.repository;

import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для управления результатами прохождения тестов.
 * Хранит историю попыток студентов и их оценки за тесты.
 */
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    
    /**
     * Находит все попытки прохождения указанного теста.
     * Используется для анализа результатов теста и статистики по группе.
     *
     * @param quiz тест, для которого нужно найти результаты
     * @return список попыток прохождения данного теста
     */
    List<QuizSubmission> findByQuiz(Quiz quiz);
    
    /**
     * Находит все тесты, пройденные указанным студентом.
     * Используется для отслеживания прогресса студента и его истории тестирования.
     *
     * @param student студент, чьи результаты нужно найти
     * @return список пройденных тестов студента
     */
    List<QuizSubmission> findByStudent(User student);
}
