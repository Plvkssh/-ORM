package com.example.lms.mapper;

import com.example.lms.dto.QuizSubmissionResponse;
import com.example.lms.dto.CreateQuizSubmissionRequest;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;

public class QuizSubmissionMapper {
    
    /**
     * Преобразует сущность QuizSubmission в DTO для ответа.
     * Извлекает ID связанных теста и студента.
     */
    public static QuizSubmissionResponse toResponse(QuizSubmission source) {
        QuizSubmissionResponse target = new QuizSubmissionResponse();
        target.setId(source.getId());
        
        Quiz quiz = source.getQuiz();
        target.setQuizId(quiz != null ? quiz.getId() : null);
        
        User student = source.getStudent();
        target.setStudentId(student != null ? student.getId() : null);
        
        target.setScore(source.getScore());
        target.setTakenAt(source.getTakenAt());
        
        return target;
    }

    /**
     * Создает новую сущность QuizSubmission на основе запроса.
     * Привязывает отправку теста к указанному тесту и студенту.
     */
    public static QuizSubmission fromRequest(CreateQuizSubmissionRequest request, 
                                           Quiz quiz, User student) {
        QuizSubmission entity = new QuizSubmission();
        entity.setQuiz(quiz);
        entity.setStudent(student);
        entity.setScore(request.getScore());
        
        return entity;
    }
}
