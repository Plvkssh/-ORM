package com.example.lms.mapper;

import com.example.lms.dto.LessonResponse;
import com.example.lms.dto.CreateLessonRequest;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;

public class LessonMapper {
    
    /**
     * Преобразует сущность Lesson в DTO для ответа.
     * Извлекает ID связанного модуля.
     */
    public static LessonResponse toResponse(Lesson source) {
        LessonResponse target = new LessonResponse();
        target.setId(source.getId());
        
        Module module = source.getModule();
        target.setModuleId(module != null ? module.getId() : null);
        
        target.setTitle(source.getTitle());
        target.setContent(source.getContent());
        target.setVideoUrl(source.getVideoUrl());
        
        return target;
    }

    /**
     * Создает новую сущность Lesson на основе запроса.
     * Привязывает урок к указанному модулю.
     */
    public static Lesson fromRequest(CreateLessonRequest request, Module module) {
        Lesson entity = new Lesson();
        entity.setModule(module);
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setVideoUrl(request.getVideoUrl());
        
        return entity;
    }

    /**
     * Обновляет существующую сущность Lesson данными из запроса.
     * Также обновляет связь с модулем.
     */
    public static void updateEntity(Lesson target, CreateLessonRequest source, Module module) {
        target.setModule(module);
        target.setTitle(source.getTitle());
        target.setContent(source.getContent());
        target.setVideoUrl(source.getVideoUrl());
    }
}
