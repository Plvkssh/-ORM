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

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final ModuleRepository moduleRepository;

    public LessonController(LessonService lessonService, ModuleRepository moduleRepository) {
        this.lessonService = lessonService;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public List<LessonResponse> findAll() { 
        return lessonService.findAll().stream()
                .map(LessonMapper::toResponse)
                .collect(Collectors.toList()); 
    }

    @GetMapping("/{id}")
    public LessonResponse getById(@PathVariable Long id) { 
        return LessonMapper.toResponse(lessonService.getById(id)); 
    }

    /**
     * Создает новый урок. Проверяет существование модуля.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonResponse create(@Valid @RequestBody CreateLessonRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        
        Lesson lesson = lessonService.create(LessonMapper.fromRequest(request, module));
        return LessonMapper.toResponse(lesson);
    }

    /**
     * Обновляет существующий урок. Проверяет существование урока и модуля.
     */
    @PutMapping("/{id}")
    public LessonResponse update(@PathVariable Long id, @Valid @RequestBody CreateLessonRequest request) {
        Lesson existingLesson = lessonService.getById(id);
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NoSuchElementException("Module not found with id: " + request.getModuleId()));
        
        LessonMapper.updateEntity(existingLesson, request, module);
        Lesson lesson = lessonService.update(id, existingLesson);
        return LessonMapper.toResponse(lesson);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { 
        lessonService.delete(id); 
    }
}
