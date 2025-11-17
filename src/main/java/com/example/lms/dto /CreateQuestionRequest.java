package com.example.lms.dto;

import com.example.lms.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateQuestionRequest {
    @NotNull(message = "ID теста обязателен для заполнения")
    private Long quizId;
    
    @NotBlank(message = "Текст вопроса не может быть пустым")
    private String text;
    
    @NotNull(message = "Тип вопроса должен быть указан")
    private QuestionType type;

    // Конструкторы
    public CreateQuestionRequest() {}
    
    public CreateQuestionRequest(Long quizId, String text, QuestionType type) {
        this.quizId = quizId;
        this.text = text;
        this.type = type;
    }

    // Геттеры и сеттеры
    public Long getQuizId() { 
        return quizId; 
    }
    
    public void setQuizId(Long quizId) { 
        this.quizId = quizId; 
    }
    
    public String getText() { 
        return text; 
    }
    
    public void setText(String text) { 
        this.text = text; 
    }
    
    public QuestionType getType() { 
        return type; 
    }
    
    public void setType(QuestionType type) { 
        this.type = type; 
    }
}
