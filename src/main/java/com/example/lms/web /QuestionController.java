package com.example.lms.web;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.CreateAnswerOptionRequest;
import com.example.lms.dto.QuestionResponse;
import com.example.lms.dto.CreateQuestionRequest;
import com.example.lms.mapper.AnswerOptionMapper;
import com.example.lms.mapper.QuestionMapper;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import com.example.lms.repository.QuestionRepository;
import com.example.lms.repository.QuizRepository;
import com.example.lms.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Контроллер для управления вопросами тестов через REST API.
 * Предоставляет endpoints для работы с вопросами и их вариантами ответов.
 */
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionService questionService, 
                             QuizRepository quizRepository, 
                             QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Получает все вопросы тестов.
     *
     * @return список всех вопросов в формате DTO
     */
    @GetMapping
    public List<QuestionResponse> findAll() { 
        return questionService.findAll().stream()
                .map(QuestionMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит вопрос по идентификатору.
     *
     * @param id идентификатор вопроса
     * @return вопрос в формате DTO
     */
    @GetMapping("/{id}")
    public QuestionResponse getById(@PathVariable Long id) { 
        return QuestionMapper.toResponse(questionService.getById(id)); 
    }

    /**
     * Создаёт новый вопрос теста.
     *
     * @param request данные для создания вопроса
     * @return созданный вопрос в формате DTO
     * @throws NoSuchElementException если тест с указанным ID не найден
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse create(@Valid @RequestBody CreateQuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + request.getQuizId()));
        Question created = questionService.create(QuestionMapper.fromRequest(request, quiz));
        return QuestionMapper.toResponse(created);
    }

    /**
     * Обновляет существующий вопрос теста.
     *
     * @param id идентификатор обновляемого вопроса
     * @param request обновлённые данные вопроса
     * @return обновлённый вопрос в формате DTO
     * @throws NoSuchElementException если тест с указанным ID не найден
     */
    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @Valid @RequestBody CreateQuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + request.getQuizId()));
        Question updated = questionService.update(id, QuestionMapper.fromRequest(request, quiz));
        return QuestionMapper.toResponse(updated);
    }

    /**
     * Удаляет вопрос теста.
     *
     * @param id идентификатор удаляемого вопроса
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        questionService.delete(id); 
    }

    /**
     * Добавляет вариант ответа к существующему вопросу.
     *
     * @param id идентификатор вопроса, к которому добавляется вариант
     * @param request данные варианта ответа
     * @return созданный вариант ответа в формате DTO
     * @throws NoSuchElementException если вопрос с указанным ID не найден
     */
    @PostMapping("/{id}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerOptionResponse addOption(@PathVariable Long id, @Valid @RequestBody CreateAnswerOptionRequest request) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));
        AnswerOption saved = questionService.addOption(AnswerOptionMapper.fromRequest(request, question));
        return AnswerOptionMapper.toResponse(saved);
    }

    /**
     * Удаляет вариант ответа.
     *
     * @param optionId идентификатор удаляемого варианта ответа
     */
    @DeleteMapping("/options/{optionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable Long optionId) { 
        questionService.deleteOption(optionId); 
    }
}
