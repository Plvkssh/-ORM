package com.example.lms.service;

import com.example.lms.model.Quiz;
import com.example.lms.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис для управления тестами в системе обучения.
 * Обеспечивает создание, обновление и удаление тестов, связанных с модулями курсов.
 */
@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    /**
     * Получает все тесты из системы.
     *
     * @return список всех тестов
     */
    public List<Quiz> findAll() { 
        return quizRepository.findAll(); 
    }

    /**
     * Находит тест по его идентификатору.
     *
     * @param id идентификатор теста
     * @return найденный тест
     * @throws NoSuchElementException если тест с указанным ID не существует
     */
    public Quiz getById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
    }

    /**
     * Создаёт новый тест в системе.
     *
     * @param quiz тест для создания
     * @return сохранённый тест с присвоенным ID
     */
    public Quiz create(Quiz quiz) { 
        return quizRepository.save(quiz); 
    }

    /**
     * Обновляет существующий тест.
     *
     * @param id идентификатор обновляемого теста
     * @param updated обновлённые данные теста
     * @return сохранённый обновлённый тест
     * @throws NoSuchElementException если тест с указанным ID не существует
     */
    public Quiz update(Long id, Quiz updated) {
        Quiz existingQuiz = getById(id);
        existingQuiz.setModule(updated.getModule());
        existingQuiz.setTitle(updated.getTitle());
        existingQuiz.setTimeLimitMinutes(updated.getTimeLimitMinutes());
        return quizRepository.save(existingQuiz);
    }

    /**
     * Удаляет тест из системы.
     *
     * @param id идентификатор удаляемого теста
     */
    public void delete(Long id) { 
        quizRepository.deleteById(id); 
    }
}
