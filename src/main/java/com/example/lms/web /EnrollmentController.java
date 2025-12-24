package com.example.lms.web;

import com.example.lms.dto.EnrollmentResponse;
import com.example.lms.dto.CreateEnrollmentRequest;
import com.example.lms.mapper.EnrollmentMapper;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentService enrollmentService, 
                               UserRepository userRepository, 
                               CourseRepository courseRepository) {
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<EnrollmentResponse> findAll() { 
        return enrollmentService.findAll().stream()
                .map(EnrollmentMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public EnrollmentResponse getById(@PathVariable Long id) { 
        return EnrollmentMapper.toResponse(enrollmentService.getById(id)); 
    }

    /**
     * Создает новую запись на курс. Проверяет существование студента и курса.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse create(@Valid @RequestBody CreateEnrollmentRequest request) {
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + request.getCourseId()));
        Enrollment created = enrollmentService.create(EnrollmentMapper.fromRequest(request, student, course));
        return EnrollmentMapper.toResponse(created);
    }

    /**
     * Обновляет существующую запись. Проверяет существование записи, студента и курса.
     */
    @PutMapping("/{id}")
    public EnrollmentResponse update(@PathVariable Long id, @Valid @RequestBody CreateEnrollmentRequest request) {
        Enrollment existingEnrollment = enrollmentService.getById(id);
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + request.getCourseId()));
        Enrollment updated = enrollmentService.update(id, EnrollmentMapper.fromRequest(request, student, course));
        return EnrollmentMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        enrollmentService.delete(id); 
    }
}
