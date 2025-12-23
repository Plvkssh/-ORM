package com.example.lms.repository;

import com.example.lms.model.Module;
import com.example.lms.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для управления тестами в системе обучения.
 * Тесты связаны с модулями курсов и содержат вопросы для проверки знаний.
 */
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
    /**
     * Находит тест, связанный с указанным модулем.
     * Каждый модуль может иметь не более одного теста (отношение один-к-одному).
     *
     * @param module модуль, для которого нужно найти тест
     * @return Optional с тестом модуля, если существует
     */
    Optional<Quiz> findByModule(Module module);
}
