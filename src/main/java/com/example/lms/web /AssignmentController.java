package com.example.lms.web;

import com.example.lms.dto.AssignmentResponse;
import com.example.lms.dto.CreateAssignmentRequest;
import com.example.lms.mapper.AssignmentMapper;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import com.example.lms.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Контроллер для управления заданиями через REST API.
 * Предоставляет endpoints для операций CRUD над заданиями.
 */
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final LessonRepository lessonRepository;

    public AssignmentController(AssignmentService assignmentService, LessonRepository lessonRepository) {
        this.assignmentService = assignmentService;
        this.lessonRepository = lessonRepository;
    }

    /**
     * Получает все задания.
     *
     * @return список всех заданий в формате DTO
     */
    @GetMapping
    public List<AssignmentResponse> findAll() { 
        return assignmentService.findAll().stream()
                .map(AssignmentMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит задание по идентификатору.
     *
     * @param id идентификатор задания
     * @return задание в формате DTO
     */
    @GetMapping("/{id}")
    public AssignmentResponse getById(@PathVariable Long id) { 
        return AssignmentMapper.toResponse(assignmentService.getById(id)); 
    }

    /**
     * Создаёт новое задание.
     *
     * @param request данные для создания задания
     * @return созданное задание в формате DTO
     * @throws NoSuchElementException если урок с указанным ID не найден
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponse create(@Valid @RequestBody CreateAssignmentRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new NoSuchElementException("Lesson not found with id: " + request.getLessonId()));
        Assignment created = assignmentService.create(AssignmentMapper.fromRequest(request, lesson));
        return AssignmentMapper.toResponse(created);
    }

    /**
     * Обновляет существующее задание.
     *
     * @param id идентификатор обновляемого задания
     * @param request обновлённые данные задания
     * @return обновлённое задание в формате DTO
     * @throws NoSuchElementException если урок с указанным ID не найден
     */
    @PutMapping("/{id}")
    public AssignmentResponse update(@PathVariable Long id, @Valid @RequestBody CreateAssignmentRequest request) {
        Assignment existingAssignment = assignmentService.getById(id);
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new NoSuchElementException("Lesson not found with id: " + request.getLessonId()));
        AssignmentMapper.updateEntity(existingAssignment, request, lesson);
        return AssignmentMapper.toResponse(assignmentService.update(id, existingAssignment));
    }

    /**
     * Удаляет задание.
     *
     * @param id идентификатор удаляемого задания
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        assignmentService.delete(id); 
    }
}
