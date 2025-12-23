package com.example.lms.web;

import com.example.lms.dto.LessonResponse;
import com.example.lms.dto.CreateLessonRequest;
import com.example.lms.mapper.LessonMapper;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;
import com.example.lms.repository.ModuleRepository;
import com.example.lms.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Контроллер для управления уроками через REST API.
 * Предоставляет endpoints для создания, получения, обновления и удаления уроков в модулях курсов.
 */
@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final ModuleRepository moduleRepository;

    public LessonController(LessonService lessonService, ModuleRepository moduleRepository) {
        this.lessonService = lessonService;
        this.moduleRepository = moduleRepository;
    }

    /**
     * Получает все уроки.
     *
     * @return список всех уроков в формате DTO
     */
    @GetMapping
    public List<LessonResponse> findAll() { 
        return lessonService.findAll().stream()
                .map(LessonMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    /**
     * Находит урок по идентификатору.
     *
     * @param id идентификатор урока
     * @return урок в формате DTO
     */
    @GetMapping("/{id}")
    public LessonResponse getById(@PathVariable Long id) { 
        return LessonMapper.toResponse(lessonService.getById(id)); 
    }

    /**
     * Создаёт новый урок.
     *
     * @param request данные для создания урока
     * @return созданный урок в формате DTO
     * @throws NoSuchElementException если модуль с указанным ID не найден
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonResponse create(@Valid @RequestBody CreateLessonRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        Lesson created = lessonService.create(LessonMapper.fromRequest(request, module));
        return LessonMapper.toResponse(created);
    }

    /**
     * Обновляет существующий урок.
     *
     * @param id идентификатор обновляемого урока
     * @param request обновлённые данные урока
     * @return обновлённый урок в формате DTO
     * @throws NoSuchElementException если модуль с указанным ID не найден
     */
    @PutMapping("/{id}")
    public LessonResponse update(@PathVariable Long id, @Valid @RequestBody CreateLessonRequest request) {
        Lesson existingLesson = lessonService.getById(id);
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        LessonMapper.updateEntity(existingLesson, request, module);
        return LessonMapper.toResponse(lessonService.update(id, existingLesson));
    }

    /**
     * Удаляет урок.
     *
     * @param id идентификатор удаляемого урока
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        lessonService.delete(id); 
    }
}
