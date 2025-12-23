package com.example.lms.web;

import com.example.lms.dto.SubmissionResponse;
import com.example.lms.dto.CreateSubmissionRequest;
import com.example.lms.mapper.SubmissionMapper;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repository.AssignmentRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Контроллер для управления решениями заданий через REST API.
 * Предоставляет endpoints для отправки решений студентами и их оценки преподавателями.
 */
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public SubmissionController(SubmissionService submissionService, 
                               AssignmentRepository assignmentRepository, 
                               UserRepository userRepository) {
        this.submissionService = submissionService;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получает все решения заданий.
     *
     * @return список всех решений в формате DTO
     */
    @GetMapping
    public List<SubmissionResponse> findAll() { 
        return submissionService.findAll().stream()
                .map(SubmissionMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит решение задания по идентификатору.
     *
     * @param id идентификатор решения
     * @return решение в формате DTO
     */
    @GetMapping("/{id}")
    public SubmissionResponse getById(@PathVariable Long id) { 
        return SubmissionMapper.toResponse(submissionService.getById(id)); 
    }

    /**
     * Создаёт новое решение задания.
     *
     * @param request данные для создания решения
     * @return созданное решение в формате DTO
     * @throws NoSuchElementException если задание или студент не найдены
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionResponse create(@Valid @RequestBody CreateSubmissionRequest request) {
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new NoSuchElementException("Assignment not found with id: " + request.getAssignmentId()));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        Submission created = submissionService.create(SubmissionMapper.fromRequest(request, assignment, student));
        return SubmissionMapper.toResponse(created);
    }

    /**
     * Оценивает решение задания преподавателем.
     * Использует PATCH для частичного обновления (только оценка и обратная связь).
     *
     * @param id идентификатор оцениваемого решения
     * @param body тело запроса с оценкой и обратной связью
     * @return обновлённое решение в формате DTO
     */
    @PatchMapping("/{id}/grade")
    public SubmissionResponse grade(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer score = body.get("score") != null ? (Integer) body.get("score") : null;
        String feedback = body.get("feedback") != null ? body.get("feedback").toString() : null;
        return SubmissionMapper.toResponse(submissionService.grade(id, score, feedback));
    }

    /**
     * Удаляет решение задания.
     *
     * @param id идентификатор удаляемого решения
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        submissionService.delete(id); 
    }
}
