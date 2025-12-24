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

    @GetMapping
    public List<QuestionResponse> findAll() { 
        return questionService.findAll().stream()
                .map(QuestionMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public QuestionResponse getById(@PathVariable Long id) { 
        return QuestionMapper.toResponse(questionService.getById(id)); 
    }

    /**
     * Создает новый вопрос теста. Проверяет существование теста.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse create(@Valid @RequestBody CreateQuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + request.getQuizId()));
        
        Question question = questionService.create(QuestionMapper.fromRequest(request, quiz));
        return QuestionMapper.toResponse(question);
    }

    /**
     * Обновляет существующий вопрос. Проверяет существование теста.
     */
    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @Valid @RequestBody CreateQuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new NoSuchElementException("Quiz not found with id: " + request.getQuizId()));
        
        Question question = questionService.update(id, QuestionMapper.fromRequest(request, quiz));
        return QuestionMapper.toResponse(question);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        questionService.delete(id); 
    }

    /**
     * Добавляет вариант ответа к вопросу. Проверяет существование вопроса.
     */
    @PostMapping("/{id}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerOptionResponse addOption(@PathVariable Long id, @Valid @RequestBody CreateAnswerOptionRequest request) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));
        
        AnswerOption option = questionService.addOption(AnswerOptionMapper.fromRequest(request, question));
        return AnswerOptionMapper.toResponse(option);
    }

    @DeleteMapping("/options/{optionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable Long optionId) { 
        questionService.deleteOption(optionId); 
    }
}
