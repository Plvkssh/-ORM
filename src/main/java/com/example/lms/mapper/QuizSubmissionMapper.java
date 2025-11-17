package com.example.lms.mapper;

import com.example.lms.dto.QuizSubmissionResponse;
import com.example.lms.dto.CreateQuizSubmissionRequest;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;

public class QuizSubmissionMapper {
    public static QuizSubmissionResponse toResponse(QuizSubmission quizSubmission) {
        QuizSubmissionResponse response = new QuizSubmissionResponse();
        response.setId(quizSubmission.getId());
        response.setQuizId(quizSubmission.getQuiz() != null ? quizSubmission.getQuiz().getId() : null);
        response.setStudentId(quizSubmission.getStudent() != null ? quizSubmission.getStudent().getId() : null);
        response.setScore(quizSubmission.getScore());
        response.setTakenAt(quizSubmission.getTakenAt());
        return response;
    }

    public static QuizSubmission fromRequest(CreateQuizSubmissionRequest request, Quiz quiz, User student) {
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setQuiz(quiz);
        quizSubmission.setStudent(student);
        quizSubmission.setScore(request.getScore());
        return quizSubmission;
    }
}
