package com.example.lms.mapper;

import com.example.lms.dto.AssignmentResponse;
import com.example.lms.dto.CreateAssignmentRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;

public class AssignmentMapper {
    
    /**
     * Преобразует сущность Assignment в DTO для ответа.
     * Извлекает ID связанного урока для включения в ответ.
     */
    public static AssignmentResponse toResponse(Assignment source) {
        AssignmentResponse target = new AssignmentResponse();
        target.setId(source.getId());
        
        Lesson relatedLesson = source.getLesson();
        target.setLessonId(relatedLesson != null ? relatedLesson.getId() : null);
        
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setDueDate(source.getDueDate());
        target.setMaxScore(source.getMaxScore());
        
        return target;
    }

    /**
     * Создает новую сущность Assignment на основе запроса.
     * Привязывает задание к указанному уроку.
     */
    public static Assignment fromRequest(CreateAssignmentRequest request, Lesson lesson) {
        Assignment entity = new Assignment();
        entity.setLesson(lesson);
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setDueDate(request.getDueDate());
        entity.setMaxScore(request.getMaxScore());
        
        return entity;
    }

    /**
     * Обновляет существующую сущность Assignment данными из запроса.
     * Также обновляет связь с уроком при необходимости.
     */
    public static void updateEntity(Assignment target, CreateAssignmentRequest source, Lesson lesson) {
        target.setLesson(lesson);
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setDueDate(source.getDueDate());
        target.setMaxScore(source.getMaxScore());
    }
}
