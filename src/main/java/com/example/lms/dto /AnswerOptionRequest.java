package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AnswerOptionRequest {
    @NotNull(message = "ID вопроса обязателен")
    private Long questionId;
    
    @NotBlank(message = "Текст варианта ответа не может быть пустым")
    private String text;
    
    private boolean correct;

    public AnswerOptionRequest() {}
    
    public AnswerOptionRequest(Long questionId, String text, boolean correct) {
        this.questionId = questionId;
        this.text = text;
        this.correct = correct;
    }

    public Long getQuestionId() { 
        return questionId; 
    }
    
    public void setQuestionId(Long questionId) { 
        this.questionId = questionId; 
    }
    
    public String getText() { 
        return text; 
    }
    
    public void setText(String text) { 
        this.text = text; 
    }
    
    public boolean isCorrect() { 
        return correct; 
    }
    
    public void setCorrect(boolean correct) { 
        this.correct = correct; 
    }
    
    @Override
    public String toString() {
        return "AnswerOptionRequest{questionId=" + questionId + 
               ", text='" + text + '\'' + 
               ", correct=" + correct + '}';
    }
}
