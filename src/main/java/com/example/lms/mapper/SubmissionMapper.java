package com.example.lms.mapper;

import com.example.lms.dto.SubmissionResponse;
import com.example.lms.dto.CreateSubmissionRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;

public class SubmissionMapper {
    
    /**
     * Преобразует сущность Submission в DTO для ответа.
     * Извлекает ID связанных задания и студента.
     */
    public static SubmissionResponse toResponse(Submission source) {
        SubmissionResponse target = new SubmissionResponse();
        target.setId(source.getId());
        
        Assignment assignment = source.getAssignment();
        target.setAssignmentId(assignment != null ? assignment.getId() : null);
        
        User student = source.getStudent();
        target.setStudentId(student != null ? student.getId() : null);
        
        target.setSubmittedAt(source.getSubmittedAt());
        target.setContent(source.getContent());
        target.setScore(source.getScore());
        target.setFeedback(source.getFeedback());
        
        return target;
    }

    /**
     * Создает новую сущность Submission на основе запроса.
     * Привязывает отправку к указанному заданию и студенту.
     */
    public static Submission fromRequest(CreateSubmissionRequest request, 
                                       Assignment assignment, User student) {
        Submission entity = new Submission();
        entity.setAssignment(assignment);
        entity.setStudent(student);
        entity.setContent(request.getContent());
        
        return entity;
    }
}
