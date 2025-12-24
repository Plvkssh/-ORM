package com.example.lms.web;

import com.example.lms.dto.QuizResponse;
import com.example.lms.dto.CreateQuizRequest;
import com.example.lms.mapper.QuizMapper;
import com.example.lms.model.Module;
import com.example.lms.model.Quiz;
import com.example.lms.repository.ModuleRepository;
import com.example.lms.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final ModuleRepository moduleRepository;

    public QuizController(QuizService quizService, ModuleRepository moduleRepository) {
        this.quizService = quizService;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public List<QuizResponse> findAll() { 
        return quizService.findAll().stream()
                .map(QuizMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public QuizResponse getById(@PathVariable Long id) { 
        return QuizMapper.toResponse(quizService.getById(id)); 
    }

    /**
     * Создает новый тест. Проверяет существование модуля.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResponse create(@Valid @RequestBody CreateQuizRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        
        Quiz quiz = quizService.create(QuizMapper.fromRequest(request, module));
        return QuizMapper.toResponse(quiz);
    }

    /**
     * Обновляет существующий тест. Проверяет существование теста и модуля.
     */
    @PutMapping("/{id}")
    public QuizResponse update(@PathVariable Long id, @Valid @RequestBody CreateQuizRequest request) {
        Quiz existingQuiz = quizService.getById(id);
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        
        QuizMapper.updateEntity(existingQuiz, request, module);
        Quiz quiz = quizService.update(id, existingQuiz);
        return QuizMapper.toResponse(quiz);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        quizService.delete(id); 
    }
}
