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

    public List<QuizSubmission> findAll() { 
        return quizSubmissionRepository.findAll(); 
    }

    /**
     * Возвращает попытку прохождения теста по ID. Если не найдена, выбрасывает исключение.
     */
    public QuizSubmission getById(Long id) { 
        return quizSubmissionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("QuizSubmission not found")); 
    }

    public QuizSubmission create(QuizSubmission quizSubmission) { 
        return quizSubmissionRepository.save(quizSubmission); 
    }

    /**
     * Обрабатывает прохождение теста студентом.
     * Проверяет ответы, вычисляет результат и сохраняет попытку.
     */
    public QuizSubmission takeQuiz(Long quizId, Long studentId, Map<Long, Long> answers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        
        List<Question> questions = quiz.getQuestions();
        int totalQuestions = questions.size();
        int correctAnswers = 0;
        
        for (Question question : questions) {
            Long selectedOptionId = answers.get(question.getId());
            if (selectedOptionId != null) {
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

    public void delete(Long id) { 
        quizSubmissionRepository.deleteById(id); 
    }
}
