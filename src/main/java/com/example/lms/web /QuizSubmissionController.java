package com.example.lms.web;

import com.example.lms.dto.QuizSubmissionResponse;
import com.example.lms.dto.CreateQuizSubmissionRequest;
import com.example.lms.mapper.QuizSubmissionMapper;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import com.example.lms.repository.QuizRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.QuizSubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Контроллер для управления результатами прохождения тестов через REST API.
 * Предоставляет endpoints для отправки ответов на тесты и просмотра результатов.
 */
@RestController
@RequestMapping("/api/quiz-submissions")
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizSubmissionController(QuizSubmissionService quizSubmissionService, 
                                   QuizRepository quizRepository, 
                                   UserRepository userRepository) {
        this.quizSubmissionService = quizSubmissionService;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получает все попытки прохождения тестов.
     *
     * @return список всех попыток в формате DTO
     */
    @GetMapping
    public List<QuizSubmissionResponse> findAll() { 
        return quizSubmissionService.findAll().stream()
                .map(QuizSubmissionMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит попытку прохождения теста по идентификатору.
     *
     * @param id идентификатор попытки
     * @return попытка прохождения теста в формате DTO
     */
    @GetMapping("/{id}")
    public QuizSubmissionResponse getById(@PathVariable Long id) { 
        return QuizSubmissionMapper.toResponse(quizSubmissionService.getById(id)); 
    }

    /**
     * Создаёт новую попытку прохождения теста.
     *
     * @param request данные для создания попытки
     * @return созданная попытка в формате DTO
     * @throws NoSuchElementException если тест или студент не найдены
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizSubmissionResponse create(@Valid @RequestBody CreateQuizSubmissionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + request.getQuizId()));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        QuizSubmission created = quizSubmissionService.create(QuizSubmissionMapper.fromRequest(request, quiz, student));
        return QuizSubmissionMapper.toResponse(created);
    }

    /**
     * Удаляет попытку прохождения теста.
     *
     * @param id идентификатор удаляемой попытки
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        quizSubmissionService.delete(id); 
    }
}
