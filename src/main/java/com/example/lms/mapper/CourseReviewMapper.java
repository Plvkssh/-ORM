package com.example.lms.mapper;

import com.example.lms.dto.CourseReviewResponse;
import com.example.lms.dto.CreateCourseReviewRequest;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;

public class CourseReviewMapper {
    
    /**
     * Преобразует сущность CourseReview в DTO для ответа.
     * Извлекает ID связанных курса и студента.
     */
    public static CourseReviewResponse toResponse(CourseReview source) {
        CourseReviewResponse target = new CourseReviewResponse();
        target.setId(source.getId());
        
        Course course = source.getCourse();
        target.setCourseId(course != null ? course.getId() : null);
        
        User student = source.getStudent();
        target.setStudentId(student != null ? student.getId() : null);
        
        target.setRating(source.getRating());
        target.setComment(source.getComment());
        target.setCreatedAt(source.getCreatedAt());
        
        return target;
    }

    /**
     * Создает новую сущность CourseReview на основе запроса.
     * Привязывает отзыв к указанному курсу и студенту.
     */
    public static CourseReview fromRequest(CreateCourseReviewRequest request, 
                                          Course course, User student) {
        CourseReview entity = new CourseReview();
        entity.setCourse(course);
        entity.setStudent(student);
        entity.setRating(request.getRating());
        entity.setComment(request.getComment());
        
        return entity;
    }
}
