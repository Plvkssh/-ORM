package com.example.lms.mapper;

import com.example.lms.dto.EnrollmentResponse;
import com.example.lms.dto.CreateEnrollmentRequest;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;

public class EnrollmentMapper {
    
    /**
     * Преобразует сущность Enrollment в DTO для ответа.
     * Извлекает ID связанных студента и курса.
     */
    public static EnrollmentResponse toResponse(Enrollment source) {
        EnrollmentResponse target = new EnrollmentResponse();
        target.setId(source.getId());
        
        User student = source.getStudent();
        target.setStudentId(student != null ? student.getId() : null);
        
        Course course = source.getCourse();
        target.setCourseId(course != null ? course.getId() : null);
        
        target.setEnrollDate(source.getEnrollDate());
        target.setStatus(source.getStatus());
        
        return target;
    }

    /**
     * Создает новую сущность Enrollment на основе запроса.
     * Устанавливает опциональные поля только если они предоставлены в запросе.
     */
    public static Enrollment fromRequest(CreateEnrollmentRequest request, User student, Course course) {
        Enrollment entity = new Enrollment();
        entity.setStudent(student);
        entity.setCourse(course);
        
        if (request.getEnrollDate() != null) {
            entity.setEnrollDate(request.getEnrollDate());
        }
        
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        
        return entity;
    }
}
