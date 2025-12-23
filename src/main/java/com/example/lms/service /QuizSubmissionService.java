package com.example.lms.service;

import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import com.example.lms.repository.QuizRepository;
import com.example.lms.repository.QuizSubmissionRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Сервис для управления прохождением тестов студентами.
 * Обрабатывает отправку ответов на тесты, вычисление результатов и сохранение попыток.
 */
@Service
@Transactional
public class QuizSubmissionService {
    
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizSubmissionService(QuizSubmissionRepository quizSubmissionRepository, 
                                QuizRepository quizRepository, 
                                UserRepository userRepository) {
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получает все попытки прохождения тестов из системы.
     *
     * @return список всех попыток прохождения тестов
     */
    public List<QuizSubmission> findAll() { 
        return quizSubmissionRepository.findAll(); 
    }

    /**
     * Находит попытку прохождения теста по её идентификатору.
     *
     * @param id идентификатор попытки
     * @return найденная попытка прохождения теста
     * @throws NoSuchElementException если попытка с указанным ID не существует
     */
    public QuizSubmission getById(Long id) { 
        return quizSubmissionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("QuizSubmission not found")); 
    }

    /**
     * Создаёт новую попытку прохождения теста.
     *
     * @param quizSubmission попытка для создания
     * @return сохранённая попытка с присвоенным ID
     */
    public QuizSubmission create(QuizSubmission quizSubmission) { 
        return quizSubmissionRepository.save(quizSubmission); 
    }

    /**
     * Обрабатывает прохождение теста студентом с вычислением результата.
     * Собирает ответы студента, проверяет их правильность и вычисляет итоговый балл.
     *
     * @param quizId идентификатор теста
     * @param studentId идентификатор студента
     * @param answers карта ответов: ключ - ID вопроса, значение - ID выбранного варианта ответа
     * @return сохранённая попытка прохождения теста с вычисленным результатом
     * @throws NoSuchElementException если тест или студент не найдены
     */
    public QuizSubmission takeQuiz(Long quizId, Long studentId, Map<Long, Long> answers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        
        // В рамках транзакции загружаем вопросы и варианты ответов
        List<Question> questions = quiz.getQuestions();
        int totalQuestions = questions.size();
        int correctAnswers = 0;
        
        for (Question question : questions) {
            Long selectedOptionId = answers.get(question.getId());
            if (selectedOptionId != null) {
                // Загружаем варианты ответов для текущего вопроса
                List<AnswerOption> options = question.getOptions();
                for (AnswerOption option : options) {
                    if (option.getId().equals(selectedOptionId) && option.isCorrect()) {
                        correctAnswers++;
                        break;
                    }
                }
            }
        }
        
        int score = totalQuestions > 0 ? (correctAnswers * 100) / totalQuestions : 0;
        
        QuizSubmission submission = new QuizSubmission();
        submission.setQuiz(quiz);
        submission.setStudent(student);
        submission.setScore(score);
        return quizSubmissionRepository.save(submission);
    }

    /**
     * Удаляет попытку прохождения теста из системы.
     *
     * @param id идентификатор удаляемой попытки
     */
    public void delete(Long id) { 
        quizSubmissionRepository.deleteById(id); 
    }
}
