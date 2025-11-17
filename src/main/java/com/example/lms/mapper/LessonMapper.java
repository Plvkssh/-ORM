package com.example.lms.mapper;

import com.example.lms.dto.LessonResponse;
import com.example.lms.dto.CreateLessonRequest;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;

public class LessonMapper {
    public static LessonResponse toResponse(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setId(lesson.getId());
        response.setModuleId(lesson.getModule() != null ? lesson.getModule().getId() : null);
        response.setTitle(lesson.getTitle());
        response.setContent(lesson.getContent());
        response.setVideoUrl(lesson.getVideoUrl());
        return response;
    }

    public static Lesson fromRequest(CreateLessonRequest request, Module module) {
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        return lesson;
    }

    public static void updateEntity(Lesson existingLesson, CreateLessonRequest request, Module module) {
        existingLesson.setModule(module);
        existingLesson.setTitle(request.getTitle());
        existingLesson.setContent(request.getContent());
        existingLesson.setVideoUrl(request.getVideoUrl());
    }
}
