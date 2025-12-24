package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.QuestionResponse;
import com.example.lms.dto.CreateQuestionRequest;
import com.example.lms.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {
    
    /**
     * Преобразует сущность Question в DTO для ответа.
     * Включает связанные варианты ответов с их преобразованием.
     */
    public static QuestionResponse toResponse(Question source) {
        QuestionResponse target = new QuestionResponse();
        target.setId(source.getId());
        
        Quiz quiz = source.getQuiz();
        target.setQuizId(quiz != null ? quiz.getId() : null);
        
        target.setText(source.getText());
        target.setType(source.getType());
        
        List<AnswerOption> options = source.getOptions();
        if (options != null) {
            List<AnswerOptionResponse> optionResponses = options.stream()
                .map(option -> mapAnswerOption(option, source.getId()))
                .collect(Collectors.toList());
            target.setOptions(optionResponses);
        }
        
        return target;
    }

    /**
     * Создает новую сущность Question на основе запроса.
     * Привязывает вопрос к указанному тесту.
     */
    public static Question fromRequest(CreateQuestionRequest request, Quiz quiz) {
        Question entity = new Question();
        entity.setQuiz(quiz);
        entity.setText(request.getText());
        entity.setType(request.getType());
        
        return entity;
    }

    /**
     * Преобразует отдельный вариант ответа в DTO.
     * Приватный вспомогательный метод для улучшения читаемости.
     */
    private static AnswerOptionResponse mapAnswerOption(AnswerOption source, Long questionId) {
        AnswerOptionResponse target = new AnswerOptionResponse();
        target.setId(source.getId());
        target.setQuestionId(questionId);
        target.setText(source.getText());
        target.setCorrect(source.isCorrect());
        
        return target;
    }
}
