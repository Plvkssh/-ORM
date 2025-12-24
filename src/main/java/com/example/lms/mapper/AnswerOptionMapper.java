package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.CreateAnswerOptionRequest;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;

public class AnswerOptionMapper {
    
    /**
     * Преобразует сущность AnswerOption в DTO для ответа.
     * Извлекает ID связанного вопроса для включения в ответ.
     */
    public static AnswerOptionResponse toResponse(AnswerOption source) {
        AnswerOptionResponse target = new AnswerOptionResponse();
        target.setId(source.getId());
        
        Question relatedQuestion = source.getQuestion();
        target.setQuestionId(relatedQuestion != null ? relatedQuestion.getId() : null);
        
        target.setText(source.getText());
        target.setCorrect(source.isCorrect());
        
        return target;
    }

    /**
     * Создает сущность AnswerOption из запроса на создание.
     * Привязывает вариант ответа к указанному вопросу.
     */
    public static AnswerOption fromRequest(CreateAnswerOptionRequest request, Question question) {
        AnswerOption entity = new AnswerOption();
        entity.setQuestion(question);
        entity.setText(request.getText());
        entity.setCorrect(request.isCorrect());
        
        return entity;
    }
}
