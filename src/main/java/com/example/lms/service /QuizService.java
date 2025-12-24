package com.example.lms.service;

import com.example.lms.model.Quiz;
import com.example.lms.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> findAll() { 
        return quizRepository.findAll(); 
    }

    /**
     * Возвращает тест по ID. Если тест не найден, выбрасывает исключение.
     */
    public Quiz getById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
    }

    public Quiz create(Quiz quiz) { 
        return quizRepository.save(quiz); 
    }

    /**
     * Обновляет существующий тест. Сначала проверяет его существование.
     */
    public Quiz update(Long id, Quiz updatedQuiz) {
        Quiz existingQuiz = getById(id);
        existingQuiz.setModule(updatedQuiz.getModule());
        existingQuiz.setTitle(updatedQuiz.getTitle());
        existingQuiz.setTimeLimitMinutes(updatedQuiz.getTimeLimitMinutes());
        return quizRepository.save(existingQuiz);
    }

    public void delete(Long id) { 
        quizRepository.deleteById(id); 
    }
}
