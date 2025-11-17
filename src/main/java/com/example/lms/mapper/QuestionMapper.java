package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.QuestionResponse;
import com.example.lms.dto.CreateQuestionRequest;
import com.example.lms.model.*;

import java.util.stream.Collectors;

public class QuestionMapper {
    public static QuestionResponse toResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setQuizId(question.getQuiz() != null ? question.getQuiz().getId() : null);
        response.setText(question.getText());
        response.setType(question.getType());
        if (question.getOptions() != null) {
            response.setOptions(question.getOptions().stream().map(option -> {
                AnswerOptionResponse optionResponse = new AnswerOptionResponse();
                optionResponse.setId(option.getId());
                optionResponse.setQuestionId(question.getId());
                optionResponse.setText(option.getText());
                optionResponse.setCorrect(option.isCorrect());
                return optionResponse;
            }).collect(Collectors.toList()));
        }
        return response;
    }

    public static Question fromRequest(CreateQuestionRequest request, Quiz quiz) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(request.getText());
        question.setType(request.getType());
        return question;
    }
}
