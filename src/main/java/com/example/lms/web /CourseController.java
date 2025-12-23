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

/**
 * Контроллер для управления курсами через REST API.
 * Предоставляет endpoints для создания, получения, обновления и удаления курсов.
 */
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

    /**
     * Получает все курсы.
     *
     * @return список всех курсов в формате DTO
     */
    @GetMapping
    public List<CourseResponse> findAll() { 
        return courseService.findAll().stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит курс по идентификатору.
     *
     * @param id идентификатор курса
     * @return курс в формате DTO
     */
    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) { 
        return CourseMapper.toResponse(courseService.getById(id)); 
    }

    /**
     * Создаёт новый курс.
     *
     * @param request данные для создания курса
     * @return созданный курс в формате DTO
     * @throws NoSuchElementException если преподаватель или тег не найдены
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
     * Обновляет существующий курс.
     *
     * @param id идентификатор обновляемого курса
     * @param request обновлённые данные курса
     * @return обновлённый курс в формате DTO
     * @throws NoSuchElementException если преподаватель или тег не найдены
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

    /**
     * Удаляет курс.
     *
     * @param id идентификатор удаляемого курса
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        courseService.delete(id); 
    }
}
