package com.example.lms.web;

import com.example.lms.dto.CourseReviewResponse;
import com.example.lms.dto.CreateCourseReviewRequest;
import com.example.lms.mapper.CourseReviewMapper;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.CourseReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class CourseReviewController {

    private final CourseReviewService courseReviewService;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseReviewController(CourseReviewService courseReviewService, 
                                 CourseRepository courseRepository, 
                                 UserRepository userRepository) {
        this.courseReviewService = courseReviewService;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<CourseReviewResponse> findAll() { 
        return courseReviewService.findAll().stream()
                .map(CourseReviewMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public CourseReviewResponse getById(@PathVariable Long id) { 
        return CourseReviewMapper.toResponse(courseReviewService.getById(id)); 
    }

    /**
     * Создает новый отзыв на курс. Проверяет существование курса и студента.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseReviewResponse create(@Valid @RequestBody CreateCourseReviewRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + request.getCourseId()));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        CourseReview created = courseReviewService.create(CourseReviewMapper.fromRequest(request, course, student));
        return CourseReviewMapper.toResponse(created);
    }

    /**
     * Обновляет существующий отзыв. Проверяет существование курса и студента.
     */
    @PutMapping("/{id}")
    public CourseReviewResponse update(@PathVariable Long id, @Valid @RequestBody CreateCourseReviewRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + request.getCourseId()));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));
        CourseReview updated = courseReviewService.update(id, CourseReviewMapper.fromRequest(request, course, student));
        return CourseReviewMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        courseReviewService.delete(id); 
    }
}
