package com.example.lms.mapper;

import com.example.lms.dto.CourseResponse;
import com.example.lms.dto.CreateCourseRequest;
import com.example.lms.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {

    /**
     * Преобразует сущность Course в DTO для ответа.
     * Извлекает ID связанных сущностей: категории, преподавателя и тегов.
     */
    public static CourseResponse toResponse(Course source) {
        CourseResponse target = new CourseResponse();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setDuration(source.getDuration());
        target.setStartDate(source.getStartDate());
        
        Category category = source.getCategory();
        target.setCategoryId(category != null ? category.getId() : null);
        
        User teacher = source.getTeacher();
        target.setTeacherId(teacher != null ? teacher.getId() : null);
        
        Set<Tag> tags = source.getTags();
        Set<Long> tagIds = tags == null ? new HashSet<>() : 
                          tags.stream().map(Tag::getId).collect(Collectors.toSet());
        target.setTagIds(tagIds);
        
        return target;
    }

    /**
     * Создает новую сущность Course на основе запроса и связанных сущностей.
     */
    public static Course fromRequest(CreateCourseRequest request, Category category, 
                                    User teacher, Set<Tag> tags) {
        Course entity = new Course();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setDuration(request.getDuration());
        entity.setStartDate(request.getStartDate());
        entity.setCategory(category);
        entity.setTeacher(teacher);
        entity.setTags(tags);
        
        return entity;
    }

    /**
     * Обновляет существующую сущность Course данными из запроса.
     * Также обновляет связи с категорией, преподавателем и тегами.
     */
    public static void updateEntity(Course target, CreateCourseRequest source, 
                                   Category category, User teacher, Set<Tag> tags) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setDuration(source.getDuration());
        target.setStartDate(source.getStartDate());
        target.setCategory(category);
        target.setTeacher(teacher);
        target.setTags(tags);
    }
}
