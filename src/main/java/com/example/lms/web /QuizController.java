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

/**
 * Контроллер для управления тестами через REST API.
 * Предоставляет endpoints для создания, получения, обновления и удаления тестов, связанных с модулями.
 */
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final ModuleRepository moduleRepository;

    public QuizController(QuizService quizService, ModuleRepository moduleRepository) {
        this.quizService = quizService;
        this.moduleRepository = moduleRepository;
    }

    /**
     * Получает все тесты.
     *
     * @return список всех тестов в формате DTO
     */
    @GetMapping
    public List<QuizResponse> findAll() { 
        return quizService.findAll().stream()
                .map(QuizMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит тест по идентификатору.
     *
     * @param id идентификатор теста
     * @return тест в формате DTO
     */
    @GetMapping("/{id}")
    public QuizResponse getById(@PathVariable Long id) { 
        return QuizMapper.toResponse(quizService.getById(id)); 
    }

    /**
     * Создаёт новый тест.
     *
     * @param request данные для создания теста
     * @return созданный тест в формате DTO
     * @throws NoSuchElementException если модуль с указанным ID не найден
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResponse create(@Valid @RequestBody CreateQuizRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        Quiz created = quizService.create(QuizMapper.fromRequest(request, module));
        return QuizMapper.toResponse(created);
    }

    /**
     * Обновляет существующий тест.
     *
     * @param id идентификатор обновляемого теста
     * @param request обновлённые данные теста
     * @return обновлённый тест в формате DTO
     * @throws NoSuchElementException если модуль с указанным ID не найден
     */
    @PutMapping("/{id}")
    public QuizResponse update(@PathVariable Long id, @Valid @RequestBody CreateQuizRequest request) {
        Quiz existingQuiz = quizService.getById(id);
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        QuizMapper.updateEntity(existingQuiz, request, module);
        return QuizMapper.toResponse(quizService.update(id, existingQuiz));
    }

    /**
     * Удаляет тест.
     *
     * @param id идентификатор удаляемого теста
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        quizService.delete(id); 
    }
}
