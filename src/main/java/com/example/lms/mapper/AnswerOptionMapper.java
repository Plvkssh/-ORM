package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.CreateAnswerOptionRequest;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;

public class AnswerOptionMapper {
    public static AnswerOptionResponse toResponse(AnswerOption answerOption) {
        AnswerOptionResponse response = new AnswerOptionResponse();
        response.setId(answerOption.getId());
        response.setQuestionId(answerOption.getQuestion() != null ? answerOption.getQuestion().getId() : null);
        response.setText(answerOption.getText());
        response.setCorrect(answerOption.isCorrect());
        return response;
    }

    public static AnswerOption fromRequest(CreateAnswerOptionRequest request, Question question) {
        AnswerOption answerOption = new AnswerOption();
        answerOption.setQuestion(question);
        answerOption.setText(request.getText());
        answerOption.setCorrect(request.isCorrect());
        return answerOption;
    }
}
