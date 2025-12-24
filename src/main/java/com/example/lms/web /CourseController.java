package com.example.lms.web;

import com.example.lms.dto.CourseResponse;
import com.example.lms.dto.CreateCourseRequest;
import com.example.lms.mapper.CourseMapper;
import com.example.lms.model.*;
import com.example.lms.repository.CategoryRepository;
import com.example.lms.repository.TagRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public CourseController(CourseService courseService, UserRepository userRepository, 
                           CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<CourseResponse> findAll() { 
        return courseService.findAll().stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) { 
        return CourseMapper.toResponse(courseService.getById(id)); 
    }

    /**
     * Создает новый курс. Проверяет существование преподавателя и тегов.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CreateCourseRequest request) {
        Category category = request.getCategoryId() == null ? null 
                : categoryRepository.findById(request.getCategoryId()).orElse(null);
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with id: " + request.getTeacherId()));
        Set<Tag> tags = Optional.ofNullable(request.getTagIds()).orElseGet(Collections::emptySet).stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new NoSuchElementException("Tag not found with id: " + tagId)))
                .collect(Collectors.toSet());
        Course created = courseService.create(CourseMapper.fromRequest(request, category, teacher, tags));
        return CourseMapper.toResponse(created);
    }

    /**
     * Обновляет существующий курс. Проверяет существование курса, преподавателя и тегов.
     */
    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CreateCourseRequest request) {
        Course existingCourse = courseService.getById(id);
        Category category = request.getCategoryId() == null ? null 
                : categoryRepository.findById(request.getCategoryId()).orElse(null);
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with id: " + request.getTeacherId()));
        Set<Tag> tags = Optional.ofNullable(request.getTagIds()).orElseGet(Collections::emptySet).stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new NoSuchElementException("Tag not found with id: " + tagId)))
                .collect(Collectors.toSet());
        CourseMapper.updateEntity(existingCourse, request, category, teacher, tags);
        return CourseMapper.toResponse(courseService.update(id, existingCourse));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        courseService.delete(id); 
    }
}
