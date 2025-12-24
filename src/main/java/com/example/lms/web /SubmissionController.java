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

    @GetMapping
    public List<SubmissionResponse> findAll() { 
        return submissionService.findAll().stream()
                .map(SubmissionMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public SubmissionResponse getById(@PathVariable Long id) { 
        return SubmissionMapper.toResponse(submissionService.getById(id)); 
    }

    /**
     * Создает новое решение задания. Проверяет существование задания и студента.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionResponse create(@Valid @RequestBody CreateSubmissionRequest request) {
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new NoSuchElementException("Assignment not found with id: " + request.getAssignmentId()));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        
        Submission submission = submissionService.create(SubmissionMapper.fromRequest(request, assignment, student));
        return SubmissionMapper.toResponse(submission);
    }

    /**
     * Оценивает решение задания. Использует PATCH для частичного обновления.
     */
    @PatchMapping("/{id}/grade")
    public SubmissionResponse grade(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer score = body.get("score") != null ? (Integer) body.get("score") : null;
        String feedback = body.get("feedback") != null ? body.get("feedback").toString() : null;
        return SubmissionMapper.toResponse(submissionService.grade(id, score, feedback));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        submissionService.delete(id); 
    }
}
