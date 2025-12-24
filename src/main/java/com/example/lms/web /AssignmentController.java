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

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final LessonRepository lessonRepository;

    public AssignmentController(AssignmentService assignmentService, LessonRepository lessonRepository) {
        this.assignmentService = assignmentService;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping
    public List<AssignmentResponse> findAll() { 
        return assignmentService.findAll().stream()
                .map(AssignmentMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public AssignmentResponse getById(@PathVariable Long id) { 
        return AssignmentMapper.toResponse(assignmentService.getById(id)); 
    }

    /**
     * Создает новое задание. Проверяет существование указанного урока.
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
     * Обновляет существующее задание. Проверяет существование задания и урока.
     */
    @PutMapping("/{id}")
    public AssignmentResponse update(@PathVariable Long id, @Valid @RequestBody CreateAssignmentRequest request) {
        Assignment existingAssignment = assignmentService.getById(id);
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new NoSuchElementException("Lesson not found with id: " + request.getLessonId()));
        AssignmentMapper.updateEntity(existingAssignment, request, lesson);
        return AssignmentMapper.toResponse(assignmentService.update(id, existingAssignment));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        assignmentService.delete(id); 
    }
}
